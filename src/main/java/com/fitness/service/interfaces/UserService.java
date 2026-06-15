package com.fitness.service.interfaces;

import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserRegisterDto;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.model.User;

import java.util.Optional;

/**
 * Service for managing users.
 */
public interface UserService extends BaseService<UserResponseDto, UserCreateDto, UserUpdateDto, Long> {

    /**
     * Finds user data for update operations.
     *
     * @param id user identifier
     * @return user data prepared for update
     * @throws EntityNotFoundException if the user is not found
     */
    UserUpdateDto findUpdateDtoById(Long id);

    /**
     * Registers a new user.
     *
     * @param dto user registration data
     * @throws ValidationException if the registration data is invalid
     */
    void registration(UserRegisterDto dto);

    /**
     * Finds a user by email address.
     *
     * @param email user email address
     * @return found user data
     * @throws EntityNotFoundException if the user is not found
     */
    UserResponseDto findByEmail(String email);
}
