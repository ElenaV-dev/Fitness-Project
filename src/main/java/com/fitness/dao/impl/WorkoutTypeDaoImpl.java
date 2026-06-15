package com.fitness.dao.impl;

import com.fitness.constants.QueryParamConstants;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
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
    private static final String SELECT_WORKOUT_TYPES_WITH_PEOPLE_COUNT = "SELECT new com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto(" +
            "wt.id, wt.title, COUNT(tr.id)) FROM WorkoutType wt LEFT JOIN TrainingRecord tr " +
            "ON tr.workoutType = wt GROUP BY wt.id, wt.title " +
            "ORDER BY wt.id ASC";

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
                .setParameter(QueryParamConstants.TITLE, title)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean existsByTitleAndIdNot(String title, Long id) {

        Long count = entityManager.createQuery(SELECT_COUNT_WORKOUT_TYPE_BY_TITLE_AND_ID_NOT, Long.class)
                .setParameter(QueryParamConstants.TITLE, title)
                .setParameter(QueryParamConstants.ID, id)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public List<TrainerWorkoutTypeDto> findWorkoutTypesWithPeopleCount() {
        TypedQuery<TrainerWorkoutTypeDto> query = entityManager.createQuery(SELECT_WORKOUT_TYPES_WITH_PEOPLE_COUNT, TrainerWorkoutTypeDto.class);
        List<TrainerWorkoutTypeDto> trainerWorkoutTypeDtos = query.getResultList();
        return trainerWorkoutTypeDtos;
    }
}
