package com.fitness.service;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.TrainingRecordDao;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.training_record_dto.TrainingRecordResponseDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.mapper.TrainingRecordMapper;
import com.fitness.mapper.WorkoutTypeMapper;
import com.fitness.model.TrainingRecord;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import com.fitness.model.WorkoutType;
import com.fitness.service.impl.TrainingRecordServiceImpl;
import com.fitness.validator.TrainingRecordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingRecordServiceImplTest {

    @Mock
    private TrainingRecordDao trainingRecordDao;

    @Mock
    private TrainingRecordValidator trainingRecordValidator;

    @Mock
    private TrainingRecordMapper trainingRecordMapper;

    @Mock
    private UserDao userDao;

    @Mock
    private WorkoutTypeDao workoutTypeDao;

    @Mock
    private WorkoutTypeMapper workoutTypeMapper;

    @InjectMocks
    private TrainingRecordServiceImpl trainingRecordService;

    private User user;
    private WorkoutType workoutType;
    private TrainingRecord trainingRecord;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setEmail("ivan@mail.com");
        user.setRole(UserRole.CLIENT);

        workoutType = new WorkoutType();
        workoutType.setId(1L);
        workoutType.setTitle("Yoga");

        trainingRecord = new TrainingRecord();
        trainingRecord.setId(1L);
        trainingRecord.setUser(user);
        trainingRecord.setWorkoutType(workoutType);
    }

    @Test
    void deleteById_ShouldDeleteTrainingRecord_WhenTrainingRecordExists() {
        when(trainingRecordDao.findById(trainingRecord.getId())).thenReturn(Optional.of(trainingRecord));

        trainingRecordService.deleteById(trainingRecord.getId());

        verify(trainingRecordDao).findById(trainingRecord.getId());
        verify(trainingRecordDao).delete(trainingRecord);
    }

    @Test
    void deleteById_ShouldThrowException_WhenTrainingRecordNotFound() {
        when(trainingRecordDao.findById(trainingRecord.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainingRecordService.deleteById(trainingRecord.getId()));

        verify(trainingRecordDao).findById(trainingRecord.getId());
        verify(trainingRecordDao, never()).delete(any());
    }

    @Test
    void bookWorkout_ShouldSaveRecord_WhenDataIsValid() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.of(workoutType));
        when(trainingRecordMapper.createToEntity(user, workoutType)).thenReturn(trainingRecord);

        trainingRecordService.bookWorkout(user.getId(), workoutType.getId());

        verify(userDao).findById(user.getId());
        verify(workoutTypeDao).findById(workoutType.getId());
        verify(trainingRecordValidator).validateCreateRecord(user, workoutType);
        verify(trainingRecordMapper).createToEntity(user, workoutType);
        verify(trainingRecordDao).save(trainingRecord);
    }

    @Test
    void bookWorkout_ShouldThrowEntityNotFoundException_WhenUserDoesNotExist() {
        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> trainingRecordService.bookWorkout(user.getId(), workoutType.getId()));

        verify(userDao).findById(user.getId());

        verify(workoutTypeDao, never()).findById(any());
        verify(trainingRecordValidator, never()).validateCreateRecord(any(), any());
        verify(trainingRecordMapper, never()).createToEntity(any(), any());
        verify(trainingRecordDao, never()).save(any());
    }

    @Test
    void bookWorkout_ShouldThrowEntityNotFoundException_WhenWorkoutTypeDoesNotExist() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> trainingRecordService.bookWorkout(user.getId(), workoutType.getId()));

        verify(userDao).findById(user.getId());
        verify(workoutTypeDao).findById(workoutType.getId());
        verify(trainingRecordValidator, never()).validateCreateRecord(any(), any());
        verify(trainingRecordMapper, never()).createToEntity(any(), any());
        verify(trainingRecordDao, never()).save(any());
    }

    @Test
    void bookWorkout_ShouldThrowValidationException_WhenValidatorFails() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.of(workoutType));

        doThrow(new ValidationException(ErrorConstants.SUBSCRIPTION_REQUIRED))
                .when(trainingRecordValidator).validateCreateRecord(user, workoutType);

        assertThrows(ValidationException.class,
                () -> trainingRecordService.bookWorkout(user.getId(), workoutType.getId()));

        verify(userDao).findById(user.getId());
        verify(workoutTypeDao).findById(workoutType.getId());
        verify(trainingRecordValidator).validateCreateRecord(user, workoutType);
        verify(trainingRecordMapper, never()).createToEntity(any(), any());
        verify(trainingRecordDao, never()).save(any());
    }

    @Test
    void findAllRecordsForUser_ShouldReturnTrainingRecordDtoList() {
        List<TrainingRecord> trainingRecords = List.of(trainingRecord, trainingRecord);
        List<TrainingRecordResponseDto> dtos = List.of(
                mock(TrainingRecordResponseDto.class),
                mock(TrainingRecordResponseDto.class));

        when(trainingRecordDao.findAllForUserId(user.getId())).thenReturn(trainingRecords);
        when(trainingRecordMapper.toResponseDtoList(trainingRecords)).thenReturn(dtos);

        List<TrainingRecordResponseDto> result = trainingRecordService.findAllRecordsForUser(user.getId());

        assertEquals(result, dtos);
    }

    @Test
    void findAllRecordsForUser_ShouldReturnEmptyList_WhenNoTrainingRecordExist() {
        List<TrainingRecord> trainingRecords = Collections.emptyList();
        List<TrainingRecordResponseDto> dtos = Collections.emptyList();

        when(trainingRecordDao.findAllForUserId(user.getId())).thenReturn(trainingRecords);
        when(trainingRecordMapper.toResponseDtoList(trainingRecords)).thenReturn(dtos);

        List<TrainingRecordResponseDto> result = trainingRecordService.findAllRecordsForUser(user.getId());

        assertTrue(result.isEmpty());
    }
}
