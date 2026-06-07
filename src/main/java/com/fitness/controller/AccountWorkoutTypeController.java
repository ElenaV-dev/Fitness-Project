package com.fitness.controller;

import com.fitness.service.interfaces.WorkoutTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/workout-types")
public class AccountWorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;

    public AccountWorkoutTypeController(WorkoutTypeService workoutTypeService) {
        this.workoutTypeService = workoutTypeService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("workoutTypes", workoutTypeService.findAll());
        model.addAttribute("activeTab", "workoutTypes");
        return "account/workout-types/list";
    }
}
