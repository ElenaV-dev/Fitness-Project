package com.fitness.service.interfaces;

import com.fitness.dto.training_record_dto.TrainingRecordCreateDto;
import com.fitness.dto.training_record_dto.TrainingRecordResponseDto;
import com.fitness.dto.training_record_dto.TrainingRecordUpdateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;

import java.util.List;

/**
 * Service for managing training records.
 */
public interface TrainingRecordService extends BaseService<TrainingRecordResponseDto,
        TrainingRecordCreateDto, TrainingRecordUpdateDto, Long> {

    /**
     * Books a workout of the specified type for the user.
     *
     * @param userId        user identifier
     * @param workoutTypeId workout type identifier
     * @throws ValidationException     if the workout has already been booked and if the user doesn't have a subscription
     * @throws EntityNotFoundException if the user or workout type does not exist
     */
    void bookWorkout(Long userId, Long workoutTypeId);

    /**
     * Returns all training records associated with the user.
     *
     * @param userId user identifier
     * @return list of the user's training records
     */
    List<TrainingRecordResponseDto> findAllRecordsForUser(Long userId);
}
