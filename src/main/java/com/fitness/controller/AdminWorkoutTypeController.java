package com.fitness.controller;

import com.fitness.service.interfaces.WorkoutTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/workout-types")
public class AdminWorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;

    public AdminWorkoutTypeController(WorkoutTypeService workoutTypeService) {
        this.workoutTypeService = workoutTypeService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("workoutTypes", workoutTypeService.findAll());
        model.addAttribute("activeTab", "workouts");
        return "admin/workout-types/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        workoutTypeService.deleteById(id);

        return "redirect:/admin/workout-types";
    }
}
