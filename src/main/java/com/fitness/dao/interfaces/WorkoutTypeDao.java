package com.fitness.dao.interfaces;

import com.fitness.model.WorkoutType;

public interface WorkoutTypeDao extends BaseDao<WorkoutType, Long> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}
