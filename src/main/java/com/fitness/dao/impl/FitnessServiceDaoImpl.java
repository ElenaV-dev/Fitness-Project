package com.fitness.dao.impl;

import com.fitness.dao.interfaces.FitnessServiceDao;
import com.fitness.model.FitnessService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FitnessServiceDaoImpl implements FitnessServiceDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_FITNESS_SERVICES = "SELECT f FROM FitnessService f";

    @Override
    public Optional<FitnessService> findById(Long id) {
        FitnessService fitnessService = entityManager.find(FitnessService.class, id);
        return Optional.ofNullable(fitnessService);
    }

    @Override
    public List<FitnessService> findAll() {
        TypedQuery<FitnessService> query = entityManager.createQuery(SELECT_ALL_FITNESS_SERVICES, FitnessService.class);
        List<FitnessService> fitnessServices = query.getResultList();
        return fitnessServices;
    }

    @Override
    public void save(FitnessService fitnessService) {
        entityManager.persist(fitnessService);
    }

    @Override
    public void update(FitnessService fitnessService) {
        entityManager.merge(fitnessService);
    }

    @Override
    public void deleteById(Long id) {
        FitnessService fitnessService = entityManager.find(FitnessService.class, id);

        if (fitnessService != null) {
            entityManager.remove(fitnessService);
        }
    }
}
