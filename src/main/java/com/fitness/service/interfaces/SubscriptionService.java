package com.fitness.service.interfaces;

import com.fitness.dto.subscription_dto.SubscriptionCreateDto;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.dto.subscription_dto.SubscriptionUpdateDto;

public interface SubscriptionService extends BaseService<SubscriptionResponseDto, SubscriptionCreateDto, SubscriptionUpdateDto, Long> {
}
