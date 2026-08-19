package com.fitness.controller;

import com.fitness.constants.UriConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminPage() {
        return UriConstants.REDIRECT_ADMIN_WORKOUT_TYPES;
    }
}
