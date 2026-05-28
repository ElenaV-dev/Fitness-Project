package com.fitness.service.interfaces;

import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.model.WorkoutType;

public interface WorkoutTypeService extends BaseService<WorkoutTypeResponseDto, WorkoutTypeCreateDto, WorkoutTypeUpdateDto, Long> {
}
