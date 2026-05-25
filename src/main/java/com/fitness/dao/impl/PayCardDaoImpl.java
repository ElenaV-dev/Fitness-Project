package com.fitness.dao.impl;

import com.fitness.dao.interfaces.PayCardDao;
import com.fitness.model.PayCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PayCardDaoImpl implements PayCardDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_PAY_CARDS = "SELECT p FROM PayCard p";

    @Override
    public Optional<PayCard> findById(Long id) {
        PayCard payCard = entityManager.find(PayCard.class, id);
        return Optional.ofNullable(payCard);
    }

    @Override
    public List<PayCard> findAll() {
        TypedQuery<PayCard> query = entityManager.createQuery(SELECT_ALL_PAY_CARDS, PayCard.class);
        List<PayCard> payCards = query.getResultList();
        return payCards;
    }

    @Override
    public void save(PayCard payCard) {
        entityManager.persist(payCard);
    }

    @Override
    public void update(PayCard payCard) {
        entityManager.merge(payCard);
    }

    @Override
    public void deleteById(Long id) {
        PayCard payCard = entityManager.find(PayCard.class, id);

        if (payCard != null) {
            entityManager.remove(payCard);
        }
    }
}
