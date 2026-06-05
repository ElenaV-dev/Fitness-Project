package com.fitness.dto.training_record_dto;

import java.util.Objects;

public class TrainingRecordResponseDto {

    private Long id;
    private String workoutTypeTitle;

    public TrainingRecordResponseDto() {}

    public TrainingRecordResponseDto(Long id, String workoutTypeTitle) {
        this.id = id;
        this.workoutTypeTitle = workoutTypeTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkoutTypeTitle() {
        return workoutTypeTitle;
    }

    public void setWorkoutTypeTitle(String workoutTypeTitle) {
        this.workoutTypeTitle = workoutTypeTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingRecordResponseDto that = (TrainingRecordResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(workoutTypeTitle, that.workoutTypeTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workoutTypeTitle);
    }
}
