package com.fitness.service.interfaces;

import com.fitness.dto.subscription_dto.SubscriptionCreateDto;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.dto.subscription_dto.SubscriptionUpdateDto;
import com.fitness.model.SubscriptionType;

public interface SubscriptionService extends BaseService<SubscriptionResponseDto, SubscriptionCreateDto, SubscriptionUpdateDto, Long> {

    void buySubscription(Long userId, SubscriptionType type);

    boolean hasActiveSubscription(Long userId);
}
