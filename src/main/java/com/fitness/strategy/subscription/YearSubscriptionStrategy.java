package com.fitness.strategy.subscription;

import com.fitness.model.SubscriptionType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class YearSubscriptionStrategy implements SubscriptionStrategy  {

    @Override
    public SubscriptionType getType() {
        return SubscriptionType.YEAR;
    }

    @Override
    public LocalDate calculateEndDate() {
        return LocalDate.now().plusYears(1);
    }
}
