package com.fitness.service.interfaces;

import com.fitness.dto.subscription_dto.SubscriptionCreateDto;
import com.fitness.dto.subscription_dto.SubscriptionResponseDto;
import com.fitness.dto.subscription_dto.SubscriptionUpdateDto;
import com.fitness.exception.EntityNotFoundException;
import com.fitness.exception.ValidationException;
import com.fitness.model.SubscriptionType;

/**
 * Service for managing user subscriptions.
 */
public interface SubscriptionService extends BaseService<SubscriptionResponseDto, SubscriptionCreateDto, SubscriptionUpdateDto, Long> {

    /**
     * Purchases a subscription of the specified type for the user.
     *
     * @param userId user identifier
     * @param type   subscription type to purchase
     * @throws ValidationException     if the user already has an active subscription
     * @throws EntityNotFoundException if the user does not exist
     */
    void buySubscription(Long userId, SubscriptionType type);

    /**
     * Checks whether the user has an active subscription.
     *
     * @param userId user identifier
     * @return {@code true} if the user has an active subscription,
     *         {@code false} otherwise
     */
    boolean hasActiveSubscription(Long userId);
}
