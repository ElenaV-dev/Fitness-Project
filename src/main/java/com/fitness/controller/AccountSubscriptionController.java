package com.fitness.controller;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.service.interfaces.SubscriptionService;
import com.fitness.service.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/subscription")
public class AccountSubscriptionController {

    private final  UserService userService;
    private final  SubscriptionService subscriptionService;

    public AccountSubscriptionController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public String subscriptionPage(Model model) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserResponseDto user = userService.findByEmail(email);

        model.addAttribute("subscription", subscriptionService.findById(user.getId()));
        model.addAttribute("hasSubscription", true);
        model.addAttribute("activeTab", "subscriptions");
        return "account/subscriptions/details";
    }
}
