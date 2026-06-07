package com.fitness.service.impl;

import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.mapper.WorkoutTypeMapper;
import com.fitness.model.User;
import com.fitness.model.WorkoutType;
import com.fitness.service.interfaces.WorkoutTypeService;
import com.fitness.validator.WorkoutTypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkoutTypeServiceImpl implements WorkoutTypeService {

    private static final Logger LOGGER = LogManager.getLogger(WorkoutTypeServiceImpl.class);

    private final WorkoutTypeDao workoutTypeDao;
    private final WorkoutTypeValidator workoutTypeValidator;
    private final WorkoutTypeMapper workoutTypeMapper;

    public WorkoutTypeServiceImpl(WorkoutTypeDao workoutTypeDao, WorkoutTypeValidator workoutTypeValidator, WorkoutTypeMapper workoutTypeMapper) {
        this.workoutTypeDao = workoutTypeDao;
        this.workoutTypeValidator = workoutTypeValidator;
        this.workoutTypeMapper = workoutTypeMapper;
    }

    @Override
    public WorkoutTypeResponseDto findById(Long id) {
        WorkoutType workoutType = workoutTypeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout type not found with id: " + id));

        return workoutTypeMapper.toResponseDto(workoutType);
    }

    @Override
    public List<WorkoutTypeResponseDto> findAll() {
        List<WorkoutType> workoutTypes = workoutTypeDao.findAll();
        return workoutTypeMapper.toResponseDtoList(workoutTypes);
    }

    @Override
    public void save(WorkoutTypeCreateDto dto) {
        workoutTypeValidator.validateCreateDto(dto);
        WorkoutType workoutType = workoutTypeMapper.createToEntity(dto);
        workoutTypeDao.save(workoutType);
        LOGGER.info("Workout type created. Title={}", dto.getTitle());
    }

    @Override
    public void update(WorkoutTypeUpdateDto dto) {
        workoutTypeValidator.validateUpdateDto(dto);

        WorkoutType workoutType = workoutTypeDao.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Workout type not found with id: " + dto.getId()));

        workoutTypeMapper.updateEntity(dto, workoutType);
        workoutTypeDao.update(workoutType);
        LOGGER.info("Workout type updated. Id={}", dto.getId());
    }

    @Override
    public void deleteById(Long id) {
        WorkoutType workoutType = workoutTypeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout type not found with id: " + id));

        workoutTypeDao.delete(workoutType);
        LOGGER.info("Workout type deleted. Id={}", id);
    }

    @Override
    public WorkoutTypeUpdateDto findUpdateDtoById(Long id) {
        WorkoutType workoutType = workoutTypeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout type not found with id: " + id));

        return workoutTypeMapper.toUpdateDto(workoutType);
    }

    @Override
    public List<TrainerWorkoutTypeDto> findWorkoutTypesForTrainer() {
        return workoutTypeDao.findWorkoutTypesWithPeopleCount();
    }
}
