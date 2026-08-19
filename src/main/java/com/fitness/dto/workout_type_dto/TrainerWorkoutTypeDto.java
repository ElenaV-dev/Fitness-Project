package com.fitness.dto.workout_type_dto;

import java.util.Objects;

public class TrainerWorkoutTypeDto {

    private Long id;
    private String title;
    private Long countPeople;

    public TrainerWorkoutTypeDto() { }

    public TrainerWorkoutTypeDto(Long id, String title, Long countPeople) {
        this.id = id;
        this.title = title;
        this.countPeople = countPeople;
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

    public Long getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(Long countPeople) {
        this.countPeople = countPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainerWorkoutTypeDto that = (TrainerWorkoutTypeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(countPeople, that.countPeople);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, countPeople);
    }
}
