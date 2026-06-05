package com.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer/workout-types")
public class TrainerWorkoutTypeController {

    @GetMapping
    public String trainerPage(Model model) {
//        model.addAttribute("workoutTypes", workoutTypeService.findAll());
        model.addAttribute("activeTab", "workoutTypes");
        return "trainer/workout-types/list";
    }
}
