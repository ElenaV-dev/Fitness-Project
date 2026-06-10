package com.fitness.validator;

import com.fitness.dao.interfaces.SubscriptionDao;
import com.fitness.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionValidator {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionValidator(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public void validateNoActiveSubscription(Long userId) {

        if (subscriptionDao.hasActiveSubscription(userId)) {
            throw new ValidationException("Active subscription already exists");
        }
    }
}
