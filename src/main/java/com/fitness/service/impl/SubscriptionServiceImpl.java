package com.fitness.service.impl;

import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.dto.subscription_dto.SubscriptionCreateDto;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.dto.subscription_dto.SubscriptionUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.mapper.SubscriptionMapper;
import com.fitness.model.Subscription;
import com.fitness.service.interfaces.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = LogManager.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionDao subscriptionDao;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao, SubscriptionMapper subscriptionMapper) {
        this.subscriptionDao = subscriptionDao;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public SubscriptionResponseDto findById(Long userId) {
        Subscription subscription = subscriptionDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found with user id: " + userId));
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
}
