package com.fitness.service.impl;

import com.fitness.constants.ErrorConstants;
import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.dao.interfaces.UserDao;
import com.fitness.dto.subscription_dto.SubscriptionCreateDto;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.dto.subscription_dto.SubscriptionUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.mapper.SubscriptionMapper;
import com.fitness.model.Subscription;
import com.fitness.model.SubscriptionType;
import com.fitness.model.User;
import com.fitness.service.interfaces.SubscriptionService;
import com.fitness.validator.SubscriptionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionValidator subscriptionValidator;
    private final UserDao userDao;

    private static final String SUBSCRIPTION_NUMBER_FORMAT = "SUB-%06d";

    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao, SubscriptionMapper subscriptionMapper, SubscriptionValidator subscriptionValidator, UserDao userDao) {
        this.subscriptionDao = subscriptionDao;
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionValidator = subscriptionValidator;
        this.userDao = userDao;
    }

    @Override
    public SubscriptionResponseDto findById(Long userId) {

        Subscription subscription = subscriptionDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.SUBSCRIPTION_NOT_FOUND_BY_USER_ID + userId));

        return subscriptionMapper.toResponseDto(subscription);
    }

    @Override
    public List<SubscriptionResponseDto> findAll() {
        return List.of();
    }

    @Override
    public void save(SubscriptionCreateDto dto) {
    }

    @Override
    public void update(SubscriptionUpdateDto dto) {
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void buySubscription(Long userId, SubscriptionType type) {

        subscriptionValidator.validateNoActiveSubscription(userId);

        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.USER_NOT_FOUND_BY_ID + userId));

        String subscriptionNumber = generateSubscriptionNumber();
        LocalDate endDate = calculateEndDate(type);
        Subscription subscription = subscriptionMapper.createSubscription(user, type, subscriptionNumber, endDate);
        subscriptionDao.save(subscription);
        LOGGER.info("Subscription purchased. UserId={}, Type={}, Number={}", userId, type, subscriptionNumber);
    }

    private String generateSubscriptionNumber() {

        String number;

        do {
            number = String.format(SUBSCRIPTION_NUMBER_FORMAT, ThreadLocalRandom.current()
                    .nextInt(100000, 1000000));
        } while (subscriptionDao.existsBySubscriptionNumber(number));

        return number;
    }

    private LocalDate calculateEndDate(SubscriptionType type) {
        return switch (type) {

            case MONTH -> LocalDate.now().plusMonths(1);

            case THREE_MONTH -> LocalDate.now().plusMonths(3);

            case YEAR -> LocalDate.now().plusYears(1);
        };
    }

    @Override
    public boolean hasActiveSubscription(Long userId) {
        return subscriptionDao.hasActiveSubscription(userId);
    }
}
