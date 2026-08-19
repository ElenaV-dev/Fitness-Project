package com.fitness.controller;

import com.fitness.constants.PageNameConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getMainPage() {
        return PageNameConstants.MAIN;
    }
}
