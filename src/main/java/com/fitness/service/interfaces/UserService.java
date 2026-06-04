package com.fitness.service.interfaces;

import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.model.User;

public interface UserService extends BaseService<UserResponseDto, UserCreateDto, UserUpdateDto, Long> {

    UserUpdateDto findUpdateDtoById(Long id);
}
