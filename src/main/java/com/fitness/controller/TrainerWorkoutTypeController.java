package com.fitness.controller;

import com.fitness.service.interfaces.WorkoutTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer/workout-types")
public class TrainerWorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;

    public TrainerWorkoutTypeController(WorkoutTypeService workoutTypeService) {
        this.workoutTypeService = workoutTypeService;
    }

    @GetMapping
    public String trainerPage(Model model) {
        model.addAttribute("workoutTypes", workoutTypeService.findWorkoutTypesForTrainer());
        model.addAttribute("activeTab", "workoutTypes");
        return "trainer/workout-types/list";
    }
}
