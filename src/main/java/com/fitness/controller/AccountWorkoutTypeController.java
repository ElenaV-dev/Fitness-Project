package com.fitness.controller;

import com.fitness.constants.PageNameConstants;
import com.fitness.constants.Tabs;
import com.fitness.constants.ViewConstants;
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

        model.addAttribute(ViewConstants.WORKOUT_TYPES, workoutTypeService.findAll());
        model.addAttribute(ViewConstants.HAS_SUBSCRIPTION, subscriptionService.hasActiveSubscription(user.getId()));
        model.addAttribute(ViewConstants.ACTIVE_TAB, Tabs.WORKOUT_TYPES);

        return PageNameConstants.ACCOUNT_WORKOUT_TYPES_LIST;
    }
}
