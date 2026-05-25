package com.fitness.dao.impl;

import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.model.WorkoutType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkoutTypeDaoImpl implements WorkoutTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_ALL_WORKOUT_TYPES = "SELECT f FROM WorkoutType f";

    @Override
    public Optional<WorkoutType> findById(Long id) {
        WorkoutType workoutType = entityManager.find(WorkoutType.class, id);
        return Optional.ofNullable(workoutType);
    }

    @Override
    public List<WorkoutType> findAll() {
        TypedQuery<WorkoutType> query = entityManager.createQuery(SELECT_ALL_WORKOUT_TYPES, WorkoutType.class);
        List<WorkoutType> workoutTypes = query.getResultList();
        return workoutTypes;
    }

    @Override
    public void save(WorkoutType workoutType) {
        entityManager.persist(workoutType);
    }

    @Override
    public void update(WorkoutType workoutType) {
        entityManager.merge(workoutType);
    }

    @Override
    public void deleteById(Long id) {
        WorkoutType workoutType = entityManager.find(WorkoutType.class, id);

        if (workoutType != null) {
            entityManager.remove(workoutType);
        }
    }
}
