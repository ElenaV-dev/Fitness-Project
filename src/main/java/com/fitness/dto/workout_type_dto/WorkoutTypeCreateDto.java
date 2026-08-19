package com.fitness.dto.workout_type_dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class WorkoutTypeCreateDto {

    @NotBlank(message = "{workout.title.required}")
    @Size(max = 100, message = "{workout.title.size}")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "{workout.title.pattern}")
    private String title;

    public WorkoutTypeCreateDto() { }

    public WorkoutTypeCreateDto(String title) {
        this.title = title;
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
        WorkoutTypeCreateDto that = (WorkoutTypeCreateDto) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
