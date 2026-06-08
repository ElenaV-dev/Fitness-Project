package com.fitness.validator;

import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.exception.ValidationException;
import com.fitness.model.User;
import com.fitness.model.WorkoutType;
import org.springframework.stereotype.Component;

@Component
public class TrainingRecordValidator {

    private final TrainingRecordDao trainingRecordDao;
    private final SubscriptionDao subscriptionDao;

    public TrainingRecordValidator(TrainingRecordDao trainingRecordDao, SubscriptionDao subscriptionDao) {
        this.trainingRecordDao = trainingRecordDao;
        this.subscriptionDao = subscriptionDao;
    }

    public void validateCreateRecord(User user, WorkoutType workoutType) {
        if (trainingRecordDao.existsByUserIdAndWorkoutTypeId(user.getId(), workoutType.getId())) {
            throw new ValidationException("You are already registered for this workout");
        }

        if (!subscriptionDao.hasActiveSubscription(user.getId())) {
            throw new ValidationException("Active subscription required");
        }
    }
}
