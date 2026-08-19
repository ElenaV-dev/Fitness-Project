package com.fitness.service;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.mapper.SubscriptionMapper;
import com.fitness.model.Subscription;
import com.fitness.model.SubscriptionType;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import com.fitness.service.impl.SubscriptionServiceImpl;
import com.fitness.strategy.subscription.SubscriptionStrategy;
import com.fitness.strategy.subscription.SubscriptionStrategyFactory;
import com.fitness.validator.SubscriptionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionDao subscriptionDao;

    @Mock
    private SubscriptionValidator subscriptionValidator;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private UserDao userDao;

    @Mock
    private SubscriptionStrategyFactory strategyFactory;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private Subscription subscription;
    private SubscriptionResponseDto responseDto;
    private User user;
    private SubscriptionStrategy strategy;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setSubscriptionNumber("SUB-123456");
        subscription.setPaid(true);
        subscription.setType(SubscriptionType.MONTH);
        subscription.setEndDate(LocalDate.now().plusMonths(1));

        responseDto = new SubscriptionResponseDto();
        responseDto.setSubscriptionNumber("SUB-123456");
        responseDto.setSubscriptionType(SubscriptionType.MONTH);
        responseDto.setEndDate(LocalDate.now().plusMonths(1));

        user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setEmail("ivan@mail.com");
        user.setRole(UserRole.CLIENT);

        strategy = mock(SubscriptionStrategy.class);
    }

    @Test
    void findById_ShouldReturnSubscriptionDto_WhenSubscriptionExists() {
        when(subscriptionDao.findById(1L)).thenReturn(Optional.of(subscription));
        when(subscriptionMapper.toResponseDto(subscription)).thenReturn(responseDto);

        SubscriptionResponseDto result = subscriptionService.findById(subscription.getId());

        assertEquals(responseDto, result);
    }

    @Test
    void findById_ShouldThrowException_WhenSubscriptionNotFound() {
        when(subscriptionDao.findById(subscription.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subscriptionService.findById(subscription.getId()));
    }

    @Test
    void buySubscription_ShouldSaveSubscription_WhenDataIsValid() {
        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(strategyFactory.getStrategy(SubscriptionType.MONTH)).thenReturn(strategy);
        when(strategy.calculateEndDate()).thenReturn(subscription.getEndDate());

        when(subscriptionMapper.createSubscription(eq(user), eq(subscription.getType()), anyString(),
                eq(subscription.getEndDate()))).thenReturn(subscription);

        subscriptionService.buySubscription(user.getId(), subscription.getType());

        verify(subscriptionValidator).validateNoActiveSubscription(user.getId());
        verify(userDao).findById(user.getId());
        verify(strategyFactory).getStrategy(subscription.getType());
        verify(subscriptionMapper).createSubscription(eq(user), eq(subscription.getType()), anyString(),
                eq(subscription.getEndDate()));
        verify(subscriptionDao).save(subscription);
    }

    @Test
    void buySubscription_ShouldThrowValidationException_WhenUserAlreadyHasActiveSubscription() {
        doThrow(new ValidationException(ErrorConstants.SUBSCRIPTION_EXISTS))
                .when(subscriptionValidator).validateNoActiveSubscription(user.getId());

        assertThrows(ValidationException.class, () -> subscriptionService.buySubscription(user.getId(), subscription.getType()));

        verify(subscriptionValidator).validateNoActiveSubscription(user.getId());
        verify(userDao, never()).findById(any());
        verify(subscriptionDao, never()).save(any());
    }

    @Test
    void buySubscription_ShouldThrowEntityNotFoundException_WhenUserDoesNotExist() {
        when(userDao.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> subscriptionService.buySubscription(user.getId(), subscription.getType()));

        verify(subscriptionValidator).validateNoActiveSubscription(user.getId());
        verify(userDao).findById(user.getId());
        verify(subscriptionMapper, never()).createSubscription(any(), any(), any(), any());
        verify(subscriptionDao, never()).save(any());
    }

    @Test
    void hasActiveSubscription_ShouldReturnTrue_WhenSubscriptionIsActive() {
        when(subscriptionDao.hasActiveSubscription(user.getId())).thenReturn(true);

        boolean result = subscriptionService.hasActiveSubscription(user.getId());

        assertTrue(result);

        verify(subscriptionDao).hasActiveSubscription(user.getId());
    }

    @Test
    void hasActiveSubscription_ShouldReturnFalse_WhenNoActiveSubscription() {
        when(subscriptionDao.hasActiveSubscription(user.getId())).thenReturn(false);

        boolean result = subscriptionService.hasActiveSubscription(user.getId());

        assertFalse(result);

        verify(subscriptionDao).hasActiveSubscription(user.getId());
    }
}

