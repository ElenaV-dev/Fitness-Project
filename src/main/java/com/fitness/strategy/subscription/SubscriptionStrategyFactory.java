package com.fitness.strategy.subscription;

import com.fitness.model.SubscriptionType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubscriptionStrategyFactory {

    private final Map<SubscriptionType, SubscriptionStrategy> strategies = new HashMap<>();

    public SubscriptionStrategyFactory(List<SubscriptionStrategy> strategyList) {

        for (SubscriptionStrategy strategy : strategyList) {
            strategies.put(strategy.getType(), strategy);
        }
    }

    public SubscriptionStrategy getStrategy(SubscriptionType type) {
        return strategies.get(type);
    }
}
