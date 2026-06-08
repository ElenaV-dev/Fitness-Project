package com.fitness.mapper;

import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.model.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

    public SubscriptionResponseDto toResponseDto(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getSubscriptionNumber(),
                subscription.getType(),
                subscription.getEndDate());
    }
}
