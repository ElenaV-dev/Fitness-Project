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

    /**
     * Returns a list of workout types for the specified page.
     *
     * @param page page number
     * @param size number of workout types per page
     * @return list of workout types
     */
    List<WorkoutTypeResponseDto> findPage(int page, int size);

    /**
     * Returns the total number of workout types.
     *
     * @return total number of workout types
     */
    long count();
}
