package com.fitness.service.impl;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.training_record_dto.TrainingRecordCreateDto;
import com.fitness.dto.training_record_dto.TrainingRecordResponseDto;
import com.fitness.dto.training_record_dto.TrainingRecordUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.mapper.TrainingRecordMapper;
import com.fitness.mapper.WorkoutTypeMapper;
import com.fitness.model.TrainingRecord;
import com.fitness.model.User;
import com.fitness.model.WorkoutType;
import com.fitness.service.interfaces.TrainingRecordService;
import com.fitness.validator.TrainingRecordValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainingRecordServiceImpl implements TrainingRecordService {

    private static final Logger LOGGER = LogManager.getLogger(TrainingRecordServiceImpl.class);

    private final UserDao userDao;
    private final WorkoutTypeDao workoutTypeDao;
    private final WorkoutTypeMapper workoutTypeMapper;
    private final TrainingRecordDao trainingRecordDao;
    private final TrainingRecordMapper trainingRecordMapper;
    private final TrainingRecordValidator trainingRecordValidator;

    public TrainingRecordServiceImpl(UserDao userDao, WorkoutTypeDao workoutTypeDao, WorkoutTypeMapper workoutTypeMapper, TrainingRecordDao trainingRecordDao, TrainingRecordMapper trainingRecordMapper, TrainingRecordValidator trainingRecordValidator) {
        this.userDao = userDao;
        this.workoutTypeDao = workoutTypeDao;
        this.workoutTypeMapper = workoutTypeMapper;
        this.trainingRecordDao = trainingRecordDao;
        this.trainingRecordMapper = trainingRecordMapper;
        this.trainingRecordValidator = trainingRecordValidator;
    }

    @Override
    public TrainingRecordResponseDto findById(Long id) {
        return null;
    }

    @Override
    public List<TrainingRecordResponseDto> findAll() {
        return List.of();
    }

    @Override
    public void save(TrainingRecordCreateDto dto) {
    }

    @Override
    public void update(TrainingRecordUpdateDto dto) {
    }

    @Override
    public void deleteById(Long id) {

        TrainingRecord trainingRecord = trainingRecordDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.TRAINING_RECORD_NOT_FOUND_BY_ID + id));

        trainingRecordDao.delete(trainingRecord);
        LOGGER.info("Training record deleted. Id={}", id);
    }

    @Override
    public void bookWorkout(Long userId, Long workoutTypeId) {

        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND_BY_ID + userId));

        WorkoutType workoutType = workoutTypeDao.findById(workoutTypeId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.WORKOUT_TYPE_NOT_FOUND_BY_ID + workoutTypeId));

        trainingRecordValidator.validateCreateRecord(user, workoutType);
        TrainingRecord record = trainingRecordMapper.createToEntity(user, workoutType);
        trainingRecordDao.save(record);
        LOGGER.info("Workout booked. UserId={}, WorkoutTypeId={}, WorkoutTitle={}",
                userId, workoutTypeId, workoutType.getTitle());
    }

    @Override
    public List<TrainingRecordResponseDto> findAllRecordsForUser(Long userId) {
        List<TrainingRecord> trainingRecords = trainingRecordDao.findAllForUserId(userId);
        return trainingRecordMapper.toResponseDtoList(trainingRecords);
    }
}
