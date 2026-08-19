package com.fitness.dto.subscription_dto;

import com.fitness.model.SubscriptionType;

import java.time.LocalDate;
import java.util.Objects;

public class SubscriptionResponseDto {

    private String subscriptionNumber;
    private SubscriptionType subscriptionType;
    private LocalDate endDate;

    public SubscriptionResponseDto() { }

    public SubscriptionResponseDto(String subscriptionNumber, SubscriptionType subscriptionType, LocalDate endDate) {
        this.subscriptionNumber = subscriptionNumber;
        this.subscriptionType = subscriptionType;
        this.endDate = endDate;
    }

    public String getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(String subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionResponseDto that = (SubscriptionResponseDto) o;
        return Objects.equals(subscriptionNumber, that.subscriptionNumber) && subscriptionType == that.subscriptionType && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionNumber, subscriptionType, endDate);
    }
}
