package com.fitness.controller;

import com.fitness.constants.*;
import com.fitness.dto.workout_type_dto.WorkoutTypeCreateDto;
import com.fitness.dto.workout_type_dto.WorkoutTypeUpdateDto;
import com.fitness.service.interfaces.WorkoutTypeService;
import com.fitness.validator.WorkoutTypeValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/workout-types")
public class AdminWorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;
    private final WorkoutTypeValidator workoutTypeValidator;

    public AdminWorkoutTypeController(WorkoutTypeService workoutTypeService, WorkoutTypeValidator workoutTypeValidator) {
        this.workoutTypeService = workoutTypeService;
        this.workoutTypeValidator = workoutTypeValidator;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute(ViewConstants.WORKOUT_TYPES, workoutTypeService.findAll());
        model.addAttribute(ViewConstants.ACTIVE_TAB, Tabs.WORKOUTS);
        return PageNameConstants.ADMIN_WORKOUT_TYPES_LIST;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute(ViewConstants.WORKOUT_TYPE, new WorkoutTypeCreateDto());
        return PageNameConstants.ADMIN_WORKOUT_TYPES_CREATE;
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("workoutType") WorkoutTypeCreateDto dto, BindingResult bindingResult) {

        if (workoutTypeValidator.titleExists(dto.getTitle())) {
            bindingResult.rejectValue(
                    FieldConstants.TITLE,
                    ValidationConstants.WORKOUT_TYPE_EXISTS);
        }

        if (bindingResult.hasErrors()) {
            return PageNameConstants.ADMIN_WORKOUT_TYPES_CREATE;
        }

        workoutTypeService.save(dto);
        return UriConstants.REDIRECT_ADMIN_WORKOUT_TYPES;
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute(ViewConstants.WORKOUT_TYPE, workoutTypeService.findUpdateDtoById(id));
        return PageNameConstants.ADMIN_WORKOUT_TYPES_EDIT;
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("workoutType") WorkoutTypeUpdateDto dto, BindingResult bindingResult) {

        if (workoutTypeValidator.titleExistsForAnotherWorkoutType(dto.getTitle(), dto.getId())) {
            bindingResult.rejectValue(
                    FieldConstants.TITLE,
                    ValidationConstants.WORKOUT_TYPE_EXISTS);
        }

        if (bindingResult.hasErrors()) {
            return PageNameConstants.ADMIN_WORKOUT_TYPES_EDIT;
        }

        workoutTypeService.update(dto);
        return UriConstants.REDIRECT_ADMIN_WORKOUT_TYPES;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        workoutTypeService.deleteById(id);
        return UriConstants.REDIRECT_ADMIN_WORKOUT_TYPES;
    }
}
