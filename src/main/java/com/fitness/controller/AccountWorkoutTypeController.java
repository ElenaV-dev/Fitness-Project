package com.fitness.controller;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.service.impl.CurrentUserService;
import com.fitness.service.interfaces.SubscriptionService;
import com.fitness.service.interfaces.WorkoutTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/workout-types")
public class AccountWorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;
    private final SubscriptionService subscriptionService;
    private final CurrentUserService currentUserService;

    public AccountWorkoutTypeController(WorkoutTypeService workoutTypeService, SubscriptionService subscriptionService, CurrentUserService currentUserService) {
        this.workoutTypeService = workoutTypeService;
        this.subscriptionService = subscriptionService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public String adminPage(Model model) {

        UserResponseDto user = currentUserService.getCurrentUser();

        model.addAttribute("workoutTypes", workoutTypeService.findAll());
        model.addAttribute("hasSubscription", subscriptionService.hasActiveSubscription(user.getId()));
        model.addAttribute("activeTab", "workoutTypes");

        return "account/workout-types/list";
    }
}
