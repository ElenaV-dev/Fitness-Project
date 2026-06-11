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
    private static final String SELECT_COUNT_TRAINING_RECORDS_BY_USER_ID_AND_WORKOUT_TYPE_ID = "SELECT COUNT(t) FROM TrainingRecord t " +
            "WHERE t.user.id = :userId AND t.workoutType.id = :workoutTypeId";
    private static final String SELECT_ALL_RECORDS_FOR_USER = "SELECT t FROM TrainingRecord t " +
                    "WHERE t.user.id = :userId";

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
    public void delete(TrainingRecord trainingRecord) {
        entityManager.remove(trainingRecord);
    }

    @Override
    public boolean existsByUserIdAndWorkoutTypeId(Long userId, Long workoutTypeId) {

        Long count = entityManager.createQuery(SELECT_COUNT_TRAINING_RECORDS_BY_USER_ID_AND_WORKOUT_TYPE_ID, Long.class)
                .setParameter("userId", userId)
                .setParameter("workoutTypeId", workoutTypeId)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public List<TrainingRecord> findAllForUserId(Long userId) {
        return entityManager.createQuery(SELECT_ALL_RECORDS_FOR_USER, TrainingRecord.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
