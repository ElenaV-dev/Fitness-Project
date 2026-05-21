package com.fitness.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "training_records")
public class TrainingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "fitness_service_id", nullable = false)
    private FitnessService fitnessService;

    public TrainingRecord() { }

    public TrainingRecord(Long id, User user, FitnessService fitnessService) {
        this.id = id;
        this.user = user;
        this.fitnessService = fitnessService;
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

    public FitnessService getFitnessService() {
        return fitnessService;
    }

    public void setFitnessService(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingRecord that = (TrainingRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


