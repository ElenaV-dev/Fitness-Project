package com.fitness.dao.impl;

import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.model.TrainingRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRecordDaoImpl implements TrainingRecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_TRAINING_RECORDS = "SELECT t FROM TrainingRecord t";

    @Override
    public Optional<TrainingRecord> findById(Long id) {
        TrainingRecord trainingRecord = entityManager.find(TrainingRecord.class, id);
        return Optional.ofNullable(trainingRecord);
    }

    @Override
    public List<TrainingRecord> findAll() {
        TypedQuery<TrainingRecord> query = entityManager.createQuery(SELECT_ALL_TRAINING_RECORDS, TrainingRecord.class);
        List<TrainingRecord> trainingRecords = query.getResultList();
        return trainingRecords;
    }

    @Override
    public void save(TrainingRecord trainingRecord) {
        entityManager.persist(trainingRecord);
    }

    @Override
    public void update(TrainingRecord trainingRecord) {
        entityManager.merge(trainingRecord);
    }

    @Override
    public void deleteById(Long id) {
        TrainingRecord trainingRecord = entityManager.find(TrainingRecord.class, id);

        if (trainingRecord != null) {
            entityManager.remove(trainingRecord);
        }
    }
}
