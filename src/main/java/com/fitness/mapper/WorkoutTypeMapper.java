package com.fitness.mapper;

import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.model.WorkoutType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutTypeMapper {

    public WorkoutTypeResponseDto toResponseDto(WorkoutType workoutType) {
        return new WorkoutTypeResponseDto(
                workoutType.getId(),
                workoutType.getTitle()
        );
    }

    public List<WorkoutTypeResponseDto> toResponseDtoList(List<WorkoutType> workoutTypes) {
        return workoutTypes.stream()
                .map(this::toResponseDto)
                .toList();
    }

    public WorkoutType createToEntity(WorkoutTypeCreateDto dto) {

        WorkoutType workoutType = new WorkoutType();

        workoutType.setTitle(dto.getTitle());

        return workoutType;
    }

    public void updateEntity(WorkoutTypeUpdateDto dto,  WorkoutType workoutType) {

        workoutType.setTitle(dto.getTitle());
    }
}
