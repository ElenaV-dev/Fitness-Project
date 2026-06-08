package com.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/subscription")
public class AccountSubscriptionController {

    @GetMapping
    public String subscriptionPage(Model model) {
        model.addAttribute("hasSubscription", false);
        model.addAttribute("activeTab", "subscriptions");
        return "account/subscriptions/details";
    }
}
