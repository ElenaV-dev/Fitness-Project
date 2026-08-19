package com.fitness.controller;

import com.fitness.constants.PageNameConstants;
import com.fitness.constants.Tabs;
import com.fitness.constants.ViewConstants;
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
        model.addAttribute(ViewConstants.WORKOUT_TYPES, workoutTypeService.findWorkoutTypesForTrainer());
        model.addAttribute(ViewConstants.ACTIVE_TAB, Tabs.WORKOUT_TYPES);
        return PageNameConstants.TRAINER_WORKOUT_TYPES_LIST;
    }
}
