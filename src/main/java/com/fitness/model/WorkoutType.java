package com.fitness.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "workout_types")
public class WorkoutType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @OneToMany(mappedBy = "workoutType", cascade = CascadeType.ALL)
    private List<TrainingRecord> trainingRecords;

    public WorkoutType() { }

    public WorkoutType(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TrainingRecord> getTrainingRecords() {
        return trainingRecords;
    }

    public void setTrainingRecords(List<TrainingRecord> trainingRecords) {
        this.trainingRecords = trainingRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutType that = (WorkoutType) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
