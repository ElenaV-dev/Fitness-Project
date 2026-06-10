package com.fitness.controller;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.ValidationException;
import com.fitness.model.SubscriptionType;
import com.fitness.service.impl.CurrentUserService;
import com.fitness.service.interfaces.SubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account/subscription")
public class AccountSubscriptionController {

    private final CurrentUserService currentUserService;
    private final SubscriptionService subscriptionService;

    public AccountSubscriptionController(CurrentUserService currentUserService, SubscriptionService subscriptionService) {
        this.currentUserService = currentUserService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public String subscriptionPage(Model model) {

        UserResponseDto user = currentUserService.getCurrentUser();

        boolean hasSubscription = subscriptionService.hasActiveSubscription(user.getId());
        model.addAttribute("hasSubscription", subscriptionService.hasActiveSubscription(user.getId()));

        if (hasSubscription) {
            model.addAttribute("subscription", subscriptionService.findById(user.getId()));
        }

        model.addAttribute("activeTab", "subscription");

        return "account/subscription/details";
    }

    @PostMapping("/buy")
    public String buySubscription(@RequestParam("type") SubscriptionType type, RedirectAttributes redirectAttributes) {

        UserResponseDto user = currentUserService.getCurrentUser();

        try {
            subscriptionService.buySubscription(user.getId(), type);
            redirectAttributes.addFlashAttribute("successMessage", "Абонемент успешно приобретен");
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/account/subscription";
    }
}
