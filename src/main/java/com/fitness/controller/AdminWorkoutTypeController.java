package com.fitness.controller;

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
        model.addAttribute("workoutTypes", workoutTypeService.findAll());
        model.addAttribute("activeTab", "workouts");
        return "admin/workout-types/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("workoutType", new WorkoutTypeCreateDto());
        return "admin/workout-types/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("workoutType") WorkoutTypeCreateDto dto, BindingResult bindingResult) {

        if (workoutTypeValidator.titleExists(dto.getTitle())) {
            bindingResult.rejectValue(
                    "title",
                    "workoutType.exists",
                    "Workout type with this title already exists");
        }

        if (bindingResult.hasErrors()) {
            return "admin/workout-types/create";
        }

        workoutTypeService.save(dto);
        return "redirect:/admin/workout-types";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("workoutType", workoutTypeService.findUpdateDtoById(id));
        return "admin/workout-types/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("workoutType") WorkoutTypeUpdateDto dto, BindingResult bindingResult) {

        if (workoutTypeValidator.titleExistsForAnotherWorkoutType(dto.getTitle(), dto.getId())) {
            bindingResult.rejectValue(
                    "title",
                    "workoutType.exists",
                    "Workout type with this title already exists");
        }

        if (bindingResult.hasErrors()) {
            return "admin/workout-types/edit";
        }

        workoutTypeService.update(dto);
        return "redirect:/admin/workout-types";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        workoutTypeService.deleteById(id);
        return "redirect:/admin/workout-types";
    }
}
