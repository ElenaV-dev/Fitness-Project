package com.fitness.dto.workout_type_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Objects;

public class WorkoutTypeUpdateDto {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "title is required")
    @Size(max = 100, message = "title must not exceed 100 characters")
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
