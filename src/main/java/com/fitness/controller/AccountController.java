package com.fitness.controller;

import com.fitness.constants.UriConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping
    public String accountPage() {
        return UriConstants.REDIRECT_ACCOUNT_WORKOUT_TYPES;
    }
}
