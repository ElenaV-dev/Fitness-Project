package com.fitness.controller;

import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.ValidationException;
import com.fitness.service.interfaces.TrainingRecordService;
import com.fitness.service.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account/training-records")
public class AccountTrainingRecordController {

    private final UserService userService;
    private final TrainingRecordService trainingRecordService;

    public AccountTrainingRecordController(UserService userService, TrainingRecordService trainingRecordService) {
        this.userService = userService;
        this.trainingRecordService = trainingRecordService;
    }

    @GetMapping
    public String myRecords(Model model) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserResponseDto user = userService.findByEmail(email);
        model.addAttribute("trainingRecords", trainingRecordService.findAllRecordsForUser(user.getId()));
        model.addAttribute("hasSubscription", false);
        model.addAttribute("activeTab", "trainingRecords");
        return "account/training-records/list";
    }

    @PostMapping("/book/{workoutTypeId}")
    public String bookWorkout(@PathVariable("workoutTypeId") Long workoutTypeId, RedirectAttributes redirectAttributes) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserResponseDto user = userService.findByEmail(email);

        try {
            trainingRecordService.bookWorkout(user.getId(), workoutTypeId);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Вы успешно записались на тренировку");
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Вы уже записаны на это занятие");
        }
        return "redirect:/account/workout-types";
    }

    @PostMapping("/cancel/{id}")
    public String cancelById(@PathVariable("id") Long id) {
        trainingRecordService.deleteById(id);
        return "redirect:/account/training-records";
    }
}
