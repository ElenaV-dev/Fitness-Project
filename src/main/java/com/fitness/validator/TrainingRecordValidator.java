package com.fitness.validator;

import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.exception.ValidationException;
import com.fitness.model.User;
import com.fitness.model.WorkoutType;
import org.springframework.stereotype.Component;

@Component
public class TrainingRecordValidator {

    private final TrainingRecordDao trainingRecordDao;

    public TrainingRecordValidator(TrainingRecordDao trainingRecordDao) {
        this.trainingRecordDao = trainingRecordDao;
    }

    public void validateCreateRecord(User user, WorkoutType workoutType) {
        if (trainingRecordDao.existsByUserIdAndWorkoutTypeId(user.getId(), workoutType.getId())) {
            throw new ValidationException("You are already registered for this workout");
        }
    }
}
