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

    private static final String SELECT_ALL_WORKOUT_TYPES = "SELECT w FROM WorkoutType w";
    private static final String SELECT_COUNT_WORKOUT_TYPE_BY_TITLE = "SELECT COUNT(w) FROM WorkoutType w " +
            "WHERE w.title = :title";
    private static final String SELECT_COUNT_WORKOUT_TYPE_BY_TITLE_AND_ID_NOT = "SELECT COUNT(w) FROM WorkoutType w " +
            "WHERE w.title = :title AND w.id <> :id";

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
    public void delete(WorkoutType workoutType) {
            entityManager.remove(workoutType);
    }

    @Override
    public boolean existsByTitle(String title) {

        Long count = entityManager.createQuery(SELECT_COUNT_WORKOUT_TYPE_BY_TITLE, Long.class)
                .setParameter("title", title)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long id) {
        Long count = entityManager.createQuery(SELECT_COUNT_WORKOUT_TYPE_BY_TITLE_AND_ID_NOT, Long.class)
                .setParameter("title", title)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
