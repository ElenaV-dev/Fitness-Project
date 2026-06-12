package com.fitness.controller;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.ValidationException;
import com.fitness.model.SubscriptionType;
import com.fitness.service.impl.CurrentUserService;
import com.fitness.service.interfaces.SubscriptionService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/account/subscription")
public class AccountSubscriptionController {

    private final CurrentUserService currentUserService;
    private final SubscriptionService subscriptionService;
    private final MessageSource messageSource;

    public AccountSubscriptionController(CurrentUserService currentUserService, SubscriptionService subscriptionService, MessageSource messageSource) {
        this.currentUserService = currentUserService;
        this.subscriptionService = subscriptionService;
        this.messageSource = messageSource;
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
        Locale locale = LocaleContextHolder.getLocale();

        try {
            subscriptionService.buySubscription(user.getId(), type);
            String successMsg = messageSource.getMessage("account.subscription.buy.success", null, locale);
            redirectAttributes.addFlashAttribute("successMessage", successMsg);
        } catch (ValidationException e) {
            String errorMsg = messageSource.getMessage("account.subscription.buy.error-exists", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
        }
        return "redirect:/account/subscription";
    }
}
