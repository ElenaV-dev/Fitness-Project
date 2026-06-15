package com.fitness.controller;

import com.fitness.constants.PageNameConstants;
import com.fitness.constants.Tabs;
import com.fitness.constants.UriConstants;
import com.fitness.constants.ViewConstants;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.exception.ValidationException;
import com.fitness.service.impl.CurrentUserService;
import com.fitness.service.interfaces.SubscriptionService;
import com.fitness.service.interfaces.TrainingRecordService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/account/training-records")
public class AccountTrainingRecordController {

    private final CurrentUserService currentUserService;
    private final TrainingRecordService trainingRecordService;
    private final SubscriptionService subscriptionService;
    private final MessageSource messageSource;

    public AccountTrainingRecordController(CurrentUserService currentUserService, TrainingRecordService trainingRecordService, SubscriptionService subscriptionService, MessageSource messageSource) {
        this.currentUserService = currentUserService;
        this.trainingRecordService = trainingRecordService;
        this.subscriptionService = subscriptionService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String myRecords(Model model) {

        UserResponseDto user = currentUserService.getCurrentUser();
        boolean hasSubscription = subscriptionService.hasActiveSubscription(user.getId());

        model.addAttribute(ViewConstants.HAS_SUBSCRIPTION, hasSubscription);

        if (hasSubscription) {
            model.addAttribute(ViewConstants.SUBSCRIPTION, subscriptionService.findById(user.getId()));
            model.addAttribute(ViewConstants.TRAINING_RECORDS, trainingRecordService.findAllRecordsForUser(user.getId()));
        }

        model.addAttribute(ViewConstants.ACTIVE_TAB, Tabs.TRAINING_RECORDS);

        return PageNameConstants.ACCOUNT_TRAINING_RECORDS_LIST;
    }

    @PostMapping("/book/{workoutTypeId}")
    public String bookWorkout(@PathVariable("workoutTypeId") Long workoutTypeId, RedirectAttributes redirectAttributes) {

        UserResponseDto user = currentUserService.getCurrentUser();
        Locale locale = LocaleContextHolder.getLocale();

        try {
            trainingRecordService.bookWorkout(user.getId(), workoutTypeId);
            String successMsg = messageSource.getMessage("account.booking.success", null, locale);
            redirectAttributes.addFlashAttribute(ViewConstants.SUCCESS_MESSAGE, successMsg);
        } catch (ValidationException e) {
            String errorMsg = messageSource.getMessage("account.booking.already-booked", null, locale);
            redirectAttributes.addFlashAttribute(ViewConstants.ERROR_MESSAGE, errorMsg);
        }
        return UriConstants.REDIRECT_ACCOUNT_WORKOUT_TYPES;
    }

    @PostMapping("/cancel/{id}")
    public String cancelById(@PathVariable("id") Long id) {
        trainingRecordService.deleteById(id);
        return UriConstants.REDIRECT_ACCOUNT_TRAINING_RECORDS;
    }
}
