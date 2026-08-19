package com.fitness.controller;

import com.fitness.constants.UriConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @GetMapping
    public String trainerPage() {
        return UriConstants.REDIRECT_TRAINER_WORKOUT_TYPES;
    }
}
