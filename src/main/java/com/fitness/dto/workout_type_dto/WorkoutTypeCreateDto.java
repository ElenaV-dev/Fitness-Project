package com.fitness.dto.workout_type_dto;

import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Objects;

public class WorkoutTypeCreateDto {

    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must not exceed 100 characters")
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
