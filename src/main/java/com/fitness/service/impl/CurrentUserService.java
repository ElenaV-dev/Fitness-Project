package com.fitness.service.impl;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.service.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service for accessing information about the currently authenticated user.
 */
@Service
public class CurrentUserService {

    private final UserService userService;

    public CurrentUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns data of the currently authenticated user.
     *
     * @return current user data
     * @throws EntityNotFoundException if the authenticated user is not found
     */
    public UserResponseDto getCurrentUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userService.findByEmail(email);
    }
}
