package com.fitness.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pay_cards")
public class PayCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number", nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "is_bound", nullable = false)
    private boolean bound;

    public PayCard() { }

    public PayCard(Long id, User user, String cardNumber, boolean bound) {
        this.id = id;
        this.user = user;
        this.cardNumber = cardNumber;
        this.bound = bound;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PayCard payCard = (PayCard) o;
        return bound == payCard.bound && Objects.equals(id, payCard.id) && Objects.equals(cardNumber, payCard.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, bound);
    }
}
