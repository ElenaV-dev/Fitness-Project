package com.fitness.validator;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class WorkoutTypeValidator {

    private final WorkoutTypeDao workoutTypeDao;

    public WorkoutTypeValidator(WorkoutTypeDao workoutTypeDao) {
        this.workoutTypeDao = workoutTypeDao;
    }

    public void validateCreateDto(WorkoutTypeCreateDto dto) {
        if (workoutTypeDao.existsByTitle(dto.getTitle())) {
            throw new ValidationException(ErrorConstants.WORKOUT_TYPE_EXISTS);
        }
    }

    public void validateUpdateDto(WorkoutTypeUpdateDto dto) {
        if (workoutTypeDao.existsByTitleAndIdNot(dto.getTitle(), dto.getId())) {
            throw new ValidationException(ErrorConstants.WORKOUT_TYPE_EXISTS);
        }
    }

    public boolean titleExists(String title) {
        return workoutTypeDao.existsByTitle(title);
    }

    public boolean titleExistsForAnotherWorkoutType(String title, Long id) {
        return workoutTypeDao.existsByTitleAndIdNot(title, id);
    }
}
