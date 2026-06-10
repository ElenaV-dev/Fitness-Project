package com.fitness.mapper;

import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.model.Subscription;
import com.fitness.model.SubscriptionType;
import com.fitness.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SubscriptionMapper {

    public SubscriptionResponseDto toResponseDto(Subscription subscription) {

        return new SubscriptionResponseDto(
                subscription.getSubscriptionNumber(),
                subscription.getType(),
                subscription.getEndDate());
    }

    public Subscription createSubscription(User user, SubscriptionType type, String subscriptionNumber,
                                           LocalDate endDate) {

        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setType(type);
        subscription.setSubscriptionNumber(subscriptionNumber);
        subscription.setPaid(true);
        subscription.setEndDate(endDate);

        return subscription;
    }
}
