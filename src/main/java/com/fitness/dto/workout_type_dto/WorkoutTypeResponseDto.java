package com.fitness.dto.workout_type_dto;

import java.util.Objects;

public class WorkoutTypeResponseDto {

    private Long id;
    private String title;

    public WorkoutTypeResponseDto() { }

    public WorkoutTypeResponseDto(Long id, String title) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutTypeResponseDto that = (WorkoutTypeResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
