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
    private WorkoutType workoutType;

    public TrainingRecord() { }

    public TrainingRecord(Long id, User user, WorkoutType workoutType) {
        this.id = id;
        this.user = user;
        this.workoutType = workoutType;
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

    public WorkoutType getFitnessService() {
        return workoutType;
    }

    public void setFitnessService(WorkoutType workoutType) {
        this.workoutType = workoutType;
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


