package com.fitness.validator;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserRegisterDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserDao userDao;

    public UserValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public void validateCreateDto(UserCreateDto dto) {
        if (userDao.existsByEmail(dto.getEmail())) {
            throw new ValidationException(ErrorConstants.EMAIL_EXISTS);
        }
    }

    public void validateUpdateDto(UserUpdateDto dto) {
        if (userDao.existsByEmailAndIdNot(dto.getEmail(), dto.getId())) {
            throw new ValidationException(ErrorConstants.EMAIL_EXISTS);
        }
    }

    public boolean emailExists(String email) {
        return userDao.existsByEmail(email);
    }

    public boolean emailExistsForAnotherUser(String email, Long id) {
        return userDao.existsByEmailAndIdNot(email, id);
    }

    public void validateRegisterDto(UserRegisterDto dto) {
        if (userDao.existsByEmail(dto.getEmail())) {
            throw new ValidationException(ErrorConstants.EMAIL_EXISTS);
        }
    }
}
