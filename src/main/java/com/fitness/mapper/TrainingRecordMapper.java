package com.fitness.mapper;

import com.fitness.dto.training_record_dto.TrainingRecordResponseDto;
import com.fitness.model.TrainingRecord;
import com.fitness.model.User;
import com.fitness.model.WorkoutType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingRecordMapper {

    public TrainingRecord createToEntity(User user, WorkoutType workoutType) {
        TrainingRecord trainingRecord = new TrainingRecord();
        trainingRecord.setUser(user);
        trainingRecord.setWorkoutType(workoutType);
        return trainingRecord;
    }

    public TrainingRecordResponseDto toResponseDto(TrainingRecord trainingRecord) {
        return new TrainingRecordResponseDto(
                trainingRecord.getId(),
                trainingRecord.getWorkoutType().getTitle());
    }

    public List<TrainingRecordResponseDto> toResponseDtoList(List<TrainingRecord> trainingRecords) {
        return trainingRecords.stream()
                .map(this::toResponseDto)
                .toList();
    }
}
