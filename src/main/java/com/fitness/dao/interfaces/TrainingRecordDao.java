package com.fitness.dao.interfaces;

import com.fitness.model.TrainingRecord;
import com.fitness.model.WorkoutType;

import java.util.List;

public interface TrainingRecordDao extends BaseDao<TrainingRecord, Long> {

    boolean existsByUserIdAndWorkoutTypeId(Long userId, Long workoutTypeId);

    List<TrainingRecord> findAllForUserId(Long userId);
}
