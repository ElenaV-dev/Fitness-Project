package com.fitness.service;

import com.fitness.constants.ErrorConstants;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import com.fitness.service.impl.CurrentUserService;
import com.fitness.service.interfaces.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrentUserServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CurrentUserService currentUserService;

    private MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;

    private User user;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        mockedSecurityContextHolder = Mockito.mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);

        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

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
    }

    @AfterEach
    void tearDown() {
        mockedSecurityContextHolder.close();
    }

    @Test
    void getCurrentUser_ShouldReturnUserDto_WhenUserIsAuthenticated() {
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(responseDto);

        UserResponseDto result = currentUserService.getCurrentUser();

        assertEquals(responseDto, result);
    }

    @Test
    void getCurrentUser_ShouldThrowException_WhenUserNotFound() {
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenThrow(new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND_BY_EMAIL));

        assertThrows(EntityNotFoundException.class, () -> currentUserService.getCurrentUser());
    }
}

