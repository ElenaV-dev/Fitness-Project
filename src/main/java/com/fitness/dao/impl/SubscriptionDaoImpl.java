package com.fitness.dao.impl;

import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.model.Subscription;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_SUBSCRIPTIONS = "SELECT s FROM Subscription s";

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
    public void deleteById(Long id) {
        Subscription subscription = entityManager.find(Subscription.class, id);

        if (subscription != null) {
            entityManager.remove(subscription);
        }
    }
}
