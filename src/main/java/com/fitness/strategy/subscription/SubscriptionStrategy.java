package com.fitness.strategy.subscription;

import com.fitness.model.SubscriptionType;

import java.time.LocalDate;

public interface SubscriptionStrategy {

    SubscriptionType getType();

    LocalDate calculateEndDate();
}
