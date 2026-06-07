package com.fitness.service.interfaces;

import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;

import java.util.List;

public interface WorkoutTypeService extends BaseService<WorkoutTypeResponseDto, WorkoutTypeCreateDto, WorkoutTypeUpdateDto, Long> {

    WorkoutTypeUpdateDto findUpdateDtoById(Long id);

    List<TrainerWorkoutTypeDto> findWorkoutTypesForTrainer();
}
