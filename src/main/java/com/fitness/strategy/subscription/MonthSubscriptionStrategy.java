package com.fitness.strategy.subscription;

import com.fitness.model.SubscriptionType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MonthSubscriptionStrategy implements SubscriptionStrategy  {

    @Override
    public SubscriptionType getType() {
        return SubscriptionType.MONTH;
    }

    @Override
    public LocalDate calculateEndDate() {
        return LocalDate.now().plusMonths(1);
    }
}
