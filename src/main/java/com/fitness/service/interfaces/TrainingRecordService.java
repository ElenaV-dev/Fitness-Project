package com.fitness.service.interfaces;

import com.fitness.dto.training_record_dto.TrainingRecordCreateDto;
import com.fitness.dto.training_record_dto.TrainingRecordResponseDto;
import com.fitness.dto.training_record_dto.TrainingRecordUpdateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;

import java.util.List;

public interface TrainingRecordService extends BaseService<TrainingRecordResponseDto,
        TrainingRecordCreateDto, TrainingRecordUpdateDto, Long> {

    void bookWorkout(Long userId, Long workoutTypeId);

    List<TrainingRecordResponseDto> findAllRecordsForUser(Long userId);
}
