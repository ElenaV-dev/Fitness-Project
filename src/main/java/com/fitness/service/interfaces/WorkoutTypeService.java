package com.fitness.service.interfaces;

import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.exception.EntityNotFoundException;

import java.util.List;

/**
 * Service for managing workout types.
 */
public interface WorkoutTypeService extends BaseService<WorkoutTypeResponseDto, WorkoutTypeCreateDto, WorkoutTypeUpdateDto, Long> {

    /**
     * Finds workout type data for update operations.
     *
     * @param id workout type identifier
     * @return workout type data prepared for update
     * @throws EntityNotFoundException if the workout type is not found
     */
    WorkoutTypeUpdateDto findUpdateDtoById(Long id);

    /**
     * Returns workout types available for trainers.
     *
     * @return list of workout types available for trainers
     */
    List<TrainerWorkoutTypeDto> findWorkoutTypesForTrainer();
}
