package com.fitness.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "subscription_number", nullable = false, unique = true, length = 20)
    private String subscriptionNumber;

    @Column(name = "is_paid", nullable = false)
    private boolean paid;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubscriptionType type;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Subscription() { }

    public Subscription(User user, String subscriptionNumber, boolean paid, SubscriptionType type, LocalDate endDate) {
        this.user = user;
        this.subscriptionNumber = subscriptionNumber;
        this.paid = paid;
        this.type = type;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(String subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
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
        Subscription that = (Subscription) o;
        return paid == that.paid && Objects.equals(user, that.user) && Objects.equals(subscriptionNumber, that.subscriptionNumber) && type == that.type && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, subscriptionNumber, paid, type, endDate);
    }
}
