package com.fitness.service;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserRegisterDto;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.mapper.UserMapper;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import com.fitness.service.impl.UserServiceImpl;
import com.fitness.validator.UserValidator;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserValidator userValidator;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponseDto responseDto;
    private UserCreateDto createDto;
    private UserUpdateDto updateDto;
    private UserRegisterDto registerDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setEmail("ivan@mail.com");
        user.setRole(UserRole.CLIENT);

        responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setFirstName("Ivan");
        responseDto.setLastName("Ivanov");
        responseDto.setEmail("ivan@mail.com");
        responseDto.setRole(UserRole.CLIENT);

        createDto = new UserCreateDto();
        createDto.setFirstName("Ivan");
        createDto.setLastName("Ivanov");
        createDto.setEmail("ivan@mail.com");
        createDto.setPassword("Password123");
        createDto.setRole(UserRole.CLIENT);

        updateDto = new UserUpdateDto();
        updateDto.setId(1L);
        updateDto.setFirstName("Ivan");
        updateDto.setLastName("Ivanov");
        updateDto.setEmail("ivan@mail.com");
        updateDto.setPassword("Password123");
        updateDto.setRole(UserRole.CLIENT);

        registerDto = new UserRegisterDto();
        registerDto.setFirstName("Ivan");
        registerDto.setLastName("Ivanov");
        registerDto.setEmail("ivan@mail.com");
        registerDto.setPassword("Password123");
    }

    @Test
    void findById_ShouldReturnUserDto_WhenUserExists() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.findById(user.getId());

        assertEquals(responseDto, result);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(user.getId()));
    }

    @Test
    void findAll_ShouldReturnUserDtoList() {
        List<User> users = List.of(user, user);
        List<UserResponseDto> dtos = List.of(
                mock(UserResponseDto.class),
                mock(UserResponseDto.class));

        when(userDao.findAll()).thenReturn(users);
        when(userMapper.toResponseDtoList(users)).thenReturn(dtos);

        List<UserResponseDto> result = userService.findAll();

        assertEquals(result, dtos);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
        List<User> users = Collections.emptyList();
        List<UserResponseDto> dtos = Collections.emptyList();

        when(userDao.findAll()).thenReturn(users);
        when(userMapper.toResponseDtoList(users)).thenReturn(dtos);

        List<UserResponseDto> result = userService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldPersistUser() {
        when(userMapper.createToEntity(createDto)).thenReturn(user);

        userService.save(createDto);

        verify(userValidator).validateCreateDto(createDto);
        verify(userDao).save(user);
    }

    @Test
    void save_ShouldThrowException_WhenValidationFails() {
        doThrow(new ValidationException(ErrorConstants.EMAIL_EXISTS))
                .when(userValidator)
                .validateCreateDto(createDto);

        assertThrows(ValidationException.class, () -> userService.save(createDto));

        verify(userValidator).validateCreateDto(createDto);
        verify(userMapper, never()).createToEntity(any());
        verify(userDao, never()).save(any());
    }

    @Test
    void update_ShouldUpdateUser_WhenUserExists() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));

        userService.update(updateDto);

        verify(userValidator).validateUpdateDto(updateDto);
        verify(userMapper).updateEntity(updateDto, user);
        verify(userDao).update(user);
    }

    @Test
    void update_ShouldThrowException_WhenValidationFails() {
        doThrow(new ValidationException(ErrorConstants.EMAIL_EXISTS))
                .when(userValidator)
                .validateUpdateDto(updateDto);

        assertThrows(ValidationException.class, () -> userService.update(updateDto));

        verify(userValidator).validateUpdateDto(updateDto);
        verify(userDao, never()).findById(any());
        verify(userMapper, never()).updateEntity(any(), any());
        verify(userDao, never()).update(any());
    }

    @Test
    void deleteById_ShouldDeleteUser_WhenUserExists() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteById(user.getId());

        verify(userDao).findById(user.getId());
        verify(userDao).delete(user);
    }

    @Test
    void deleteById_ShouldThrowException_WhenUserNotFound() {
        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteById(user.getId()));

        verify(userDao).findById(user.getId());
        verify(userDao, never()).delete(any());
    }

    @Test
    void findUpdateDtoById_ShouldReturnUpdateDto_WhenUserExists() {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUpdateDto(user)).thenReturn(updateDto);

        UserUpdateDto result = userService.findUpdateDtoById(user.getId());

        assertEquals(updateDto, result);
    }

    @Test
    void findUpdateDtoById_ShouldThrowException_WhenUserNotFound() {
        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findUpdateDtoById(user.getId()));
    }

    @Test
    void registration_ShouldRegisterUser() {
        when(userMapper.registerToEntity(registerDto)).thenReturn(user);

        userService.registration(registerDto);

        verify(userValidator).validateRegisterDto(registerDto);
        verify(userMapper).registerToEntity(registerDto);
        verify(userDao).save(user);
    }

    @Test
    void registration_ShouldThrowException_WhenValidationFails() {
        doThrow(new ValidationException(ErrorConstants.EMAIL_EXISTS))
                .when(userValidator)
                .validateRegisterDto(registerDto);

        assertThrows(ValidationException.class, () -> userService.registration(registerDto));

        verify(userValidator).validateRegisterDto(registerDto);
        verify(userMapper, never()).registerToEntity(any());
        verify(userDao, never()).save(any());
    }

    @Test
    void findByEmail_ShouldReturnUserDto_WhenUserExists() {
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.findByEmail(user.getEmail());

        assertEquals(responseDto, result);
    }

    @Test
    void findByEmail_ShouldThrowException_WhenUserNotFound() {
        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(user.getEmail()));
    }
}
