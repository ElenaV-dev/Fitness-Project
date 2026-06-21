package com.fitness.service;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.WorkoutTypeDao;
import com.fitness.dto.workout_type_dto.TrainerWorkoutTypeDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeResponseDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.mapper.WorkoutTypeMapper;
import com.fitness.model.WorkoutType;
import com.fitness.service.impl.WorkoutTypeServiceImpl;
import com.fitness.validator.WorkoutTypeValidator;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WorkoutTypeServiceImplTest {

    @Mock
    private WorkoutTypeDao workoutTypeDao;

    @Mock
    private WorkoutTypeValidator workoutTypeValidator;

    @Mock
    private WorkoutTypeMapper workoutTypeMapper;

    @InjectMocks
    private WorkoutTypeServiceImpl workoutTypeService;

    private WorkoutType workoutType;
    private WorkoutTypeResponseDto responseDto;
    private WorkoutTypeCreateDto createDto;
    private WorkoutTypeUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        workoutType = new WorkoutType();
        workoutType.setId(1L);
        workoutType.setTitle("Yoga");

        responseDto = new WorkoutTypeResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Yoga");

        createDto = new WorkoutTypeCreateDto();
        createDto.setTitle("Yoga");

        updateDto = new WorkoutTypeUpdateDto();
        updateDto.setId(1L);
        updateDto.setTitle("Yoga");


    }

    @Test
    void findById_ShouldReturnWorkoutTypeDto_WhenWorkoutTypeExists() {
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.of(workoutType));
        when(workoutTypeMapper.toResponseDto(workoutType)).thenReturn(responseDto);

        WorkoutTypeResponseDto result = workoutTypeService.findById(workoutType.getId());

        assertEquals(responseDto, result);
    }

    @Test
    void findById_ShouldThrowException_WhenWorkoutTypeNotFound() {
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutTypeService.findById(workoutType.getId()));
    }

    @Test
    void findAll_ShouldReturnWorkoutTypeDtoList() {
        List<WorkoutType> workoutTypes = List.of(workoutType, workoutType);
        List<WorkoutTypeResponseDto> dtos = List.of(
                mock(WorkoutTypeResponseDto.class),
                mock(WorkoutTypeResponseDto.class));

        when(workoutTypeDao.findAll()).thenReturn(workoutTypes);
        when(workoutTypeMapper.toResponseDtoList(workoutTypes)).thenReturn(dtos);

        List<WorkoutTypeResponseDto> result = workoutTypeService.findAll();

        assertEquals(result, dtos);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoWorkoutTypesExist() {
        List<WorkoutType> workoutTypes = Collections.emptyList();
        List<WorkoutTypeResponseDto> dtos = Collections.emptyList();

        when(workoutTypeDao.findAll()).thenReturn(workoutTypes);
        when(workoutTypeMapper.toResponseDtoList(workoutTypes)).thenReturn(dtos);

        List<WorkoutTypeResponseDto> result = workoutTypeService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldPersistWorkoutType() {
        when(workoutTypeMapper.createToEntity(createDto)).thenReturn(workoutType);

        workoutTypeService.save(createDto);

        verify(workoutTypeValidator).validateCreateDto(createDto);
        verify(workoutTypeDao).save(workoutType);
    }

    @Test
    void save_ShouldThrowException_WhenValidationFails() {
        doThrow(new ValidationException(ErrorConstants.WORKOUT_TYPE_EXISTS))
                .when(workoutTypeValidator)
                .validateCreateDto(createDto);

        assertThrows(ValidationException.class, () -> workoutTypeService.save(createDto));

        verify(workoutTypeValidator).validateCreateDto(createDto);
        verify(workoutTypeMapper, never()).createToEntity(any());
        verify(workoutTypeDao, never()).save(any());
    }

    @Test
    void update_ShouldUpdateWorkoutType_WhenWorkoutTypeExists() {
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.of(workoutType));

        workoutTypeService.update(updateDto);

        verify(workoutTypeValidator).validateUpdateDto(updateDto);
        verify(workoutTypeMapper).updateEntity(updateDto, workoutType);
        verify(workoutTypeDao).update(workoutType);
    }

    @Test
    void update_ShouldThrowException_WhenValidationFails() {
        doThrow(new ValidationException(ErrorConstants.WORKOUT_TYPE_EXISTS))
                .when(workoutTypeValidator)
                .validateUpdateDto(updateDto);

        assertThrows(ValidationException.class, () -> workoutTypeService.update(updateDto));

        verify(workoutTypeValidator).validateUpdateDto(updateDto);
        verify(workoutTypeDao, never()).findById(any());
        verify(workoutTypeMapper, never()).updateEntity(any(), any());
        verify(workoutTypeDao, never()).update(any());
    }

    @Test
    void deleteById_ShouldDeleteWorkoutType_WhenWorkoutTypeExists() {
        when(workoutTypeDao.findById(1L)).thenReturn(Optional.of(workoutType));

        workoutTypeService.deleteById(workoutType.getId());

        verify(workoutTypeDao).findById(workoutType.getId());
        verify(workoutTypeDao).delete(workoutType);
    }

    @Test
    void deleteById_ShouldThrowException_WhenWorkoutTypeNotFound() {
        when(workoutTypeDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutTypeService.deleteById(1L));

        verify(workoutTypeDao).findById(1L);
        verify(workoutTypeDao, never()).delete(any());
    }

    @Test
    void findUpdateDtoById_ShouldReturnUpdateDto_WhenWorkoutTypeExists() {
        when(workoutTypeDao.findById(1L)).thenReturn(Optional.of(workoutType));
        when(workoutTypeMapper.toUpdateDto(workoutType)).thenReturn(updateDto);

        WorkoutTypeUpdateDto result = workoutTypeService.findUpdateDtoById(workoutType.getId());

        assertEquals(updateDto, result);
    }

    @Test
    void findUpdateDtoById_ShouldThrowException_WhenWorkoutTypeNotFound() {
        when(workoutTypeDao.findById(workoutType.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> workoutTypeService.findUpdateDtoById(workoutType.getId()));
    }

    @Test
    void findWorkoutTypesForTrainer_ShouldReturnDtoList() {
        List<TrainerWorkoutTypeDto> dtos = List.of(
                mock(TrainerWorkoutTypeDto.class),
                mock(TrainerWorkoutTypeDto.class));

        when(workoutTypeDao.findWorkoutTypesWithPeopleCount()).thenReturn(dtos);

        List<TrainerWorkoutTypeDto> result = workoutTypeService.findWorkoutTypesForTrainer();

        assertEquals(dtos, result);
    }

    @Test
    void findWorkoutTypesForTrainer_ShouldReturnEmptyList_WhenNoWorkoutTypesExist() {
        List<TrainerWorkoutTypeDto> dtos = Collections.emptyList();

        when(workoutTypeDao.findWorkoutTypesWithPeopleCount()).thenReturn(dtos);

        List<TrainerWorkoutTypeDto> result = workoutTypeService.findWorkoutTypesForTrainer();

        assertTrue(result.isEmpty());
    }

    @Test
    void findPage_ShouldReturnPage() {
        List<WorkoutTypeResponseDto> dtos = List.of(responseDto, responseDto);
        List<WorkoutType> workoutTypes = List.of(workoutType, workoutType);
        int page = 1;
        int size = 5;

        when(workoutTypeDao.findPage(page, size)).thenReturn(workoutTypes);
        when(workoutTypeMapper.toResponseDtoList(workoutTypes)).thenReturn(dtos);

        List<WorkoutTypeResponseDto> result = workoutTypeService.findPage(page, size);

        assertEquals(dtos, result);
    }

    @Test
    void findPage_ShouldReturnEmptyPage_WhenNoPageExist() {
        List<WorkoutType> workoutTypes = Collections.emptyList();
        List<WorkoutTypeResponseDto> dtos = Collections.emptyList();
        int page = 1;
        int size = 5;

        when(workoutTypeDao.findPage(page, size)).thenReturn(workoutTypes);
        when(workoutTypeMapper.toResponseDtoList(workoutTypes)).thenReturn(dtos);

        List<WorkoutTypeResponseDto> result = workoutTypeService.findPage(page, size);

        assertTrue(result.isEmpty());
    }

    @Test
    void count_ShouldReturnCountPage() {
        long count = 20;

        when(workoutTypeDao.count()).thenReturn(count);

        long result = workoutTypeService.count();

        assertEquals(count, result);
    }

    @Test
    void count_ShouldReturnZero_WhenNoWorkoutTypesExist() {
        long count = 0;

        when(workoutTypeDao.count()).thenReturn(count);

        long result = workoutTypeService.count();

        assertEquals(count, result);
    }
}
