package com.fitness.dao.interfaces;

import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.model.WorkoutType;

import java.util.List;

public interface WorkoutTypeDao extends BaseDao<WorkoutType, Long> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    List<TrainerWorkoutTypeDto> findWorkoutTypesWithPeopleCount();

    List<WorkoutType> findPage(int page, int size);

    Long count();
}
