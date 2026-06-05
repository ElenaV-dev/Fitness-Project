package com.fitness.service.impl;

import com.fitness.dao.interfaces.UserDao;
import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserRegisterDto;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.mapper.UserMapper;
import com.fitness.model.User;
import com.fitness.service.interfaces.UserService;
import com.fitness.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final UserValidator userValidator;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userDao, UserValidator userValidator, UserMapper userMapper) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<User> users = userDao.findAll();
        return userMapper.toResponseDtoList(users);
    }

    @Override
    public void save(UserCreateDto dto) {
        userValidator.validateCreateDto(dto);
        User user = userMapper.createToEntity(dto);
        userDao.save(user);
        LOGGER.info("User created with email {}", user.getEmail());
    }

    @Override
    public void update(UserUpdateDto dto) {
        userValidator.validateUpdateDto(dto);

        User user = userDao.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getId()));

        userMapper.updateEntity(dto, user);
        userDao.update(user);
        LOGGER.info("User updated. Id={}", dto.getId());
    }

    @Override
    public void deleteById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userDao.delete(user);
        LOGGER.info("User deleted. Id={}", id);
    }

    @Override
    public UserUpdateDto findUpdateDtoById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        return userMapper.toUpdateDto(user);
    }

    @Override
    public void registration(UserRegisterDto dto) {
        userValidator.validateRegisterDto(dto);
        User user = userMapper.registerToEntity(dto);
        userDao.save(user);
        LOGGER.info("User created with email {}", user.getEmail());
    }

    @Override
    public UserResponseDto findByEmail(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        return userMapper.toResponseDto(user);
    }
}
