package com.fitness.dto.subscription_dto;

import com.fitness.model.SubscriptionType;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class SubscriptionCreateDto {

    @NotNull
    private SubscriptionType type;

    public SubscriptionCreateDto() { }

    public SubscriptionCreateDto(SubscriptionType type) {
        this.type = type;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionCreateDto that = (SubscriptionCreateDto) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}
