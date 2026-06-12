package com.fitness.dto.workout_type_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class WorkoutTypeUpdateDto {

    @NotNull(message = "{workout.id.required}")
    private Long id;

    @NotBlank(message = "{workout.title.required}")
    @Size(max = 100, message = "{workout.title.size}")
    private String title;

    public WorkoutTypeUpdateDto() { }

    public WorkoutTypeUpdateDto(Long id, String title) {
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
        WorkoutTypeUpdateDto that = (WorkoutTypeUpdateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
