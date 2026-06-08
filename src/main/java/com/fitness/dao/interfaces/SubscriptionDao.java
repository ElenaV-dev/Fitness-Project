package com.fitness.dao.interfaces;

import com.fitness.model.Subscription;

public interface SubscriptionDao extends BaseDao<Subscription, Long> {

    boolean hasActiveSubscription(Long userId);
}
