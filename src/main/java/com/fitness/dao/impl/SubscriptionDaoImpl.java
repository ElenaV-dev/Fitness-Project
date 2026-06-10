package com.fitness.dao.impl;

import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.model.Subscription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_SUBSCRIPTIONS = "SELECT s FROM Subscription s";
    private static final String SELECT_ACTIVE_SUBSCRIPTION_COUNT = "SELECT COUNT(s) FROM Subscription s " +
                    "WHERE s.user.id = :userId AND s.paid = true AND s.endDate >= :today";
    private static final String SELECT_SUBSCRIPTION_BY_NUMBER = "SELECT COUNT(s) FROM Subscription s " +
                    "WHERE s.subscriptionNumber = :number";

    @Override
    public Optional<Subscription> findById(Long id) {

        Subscription subscription = entityManager.find(Subscription.class, id);
        return Optional.ofNullable(subscription);
    }

    @Override
    public List<Subscription> findAll() {

        TypedQuery<Subscription> query = entityManager.createQuery(SELECT_ALL_SUBSCRIPTIONS, Subscription.class);
        List<Subscription> subscriptions = query.getResultList();
        return subscriptions;
    }

    @Override
    public void save(Subscription subscription) {
        entityManager.persist(subscription);
    }

    @Override
    public void update(Subscription subscription) {
        entityManager.merge(subscription);
    }

    @Override
    public void delete(Subscription subscription) {
        entityManager.remove(subscription);
    }

    @Override
    public boolean hasActiveSubscription(Long userId) {

        Long count = entityManager.createQuery(SELECT_ACTIVE_SUBSCRIPTION_COUNT, Long.class)
                .setParameter("userId", userId)
                .setParameter("today", LocalDate.now())
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean existsBySubscriptionNumber(String number) {

        Long count = entityManager.createQuery(SELECT_SUBSCRIPTION_BY_NUMBER, Long.class)
                .setParameter("number", number )
                .getSingleResult();

        return count > 0;
    }
}
