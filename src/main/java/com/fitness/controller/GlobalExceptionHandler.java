package com.fitness.controller;

import com.fitness.constants.PageNameConstants;
import com.fitness.constants.ViewConstants;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException ex, Model model) {
        LOGGER.warn("Entity not found: {}", ex.getMessage());
        Locale locale = LocaleContextHolder.getLocale();
        String translatedMessage = messageSource.getMessage("error.404.default-msg", null, locale);
        model.addAttribute(ViewConstants.MESSAGE, translatedMessage);
        return PageNameConstants.ERROR_404;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        LOGGER.error("Unexpected error", ex);
        Locale locale = LocaleContextHolder.getLocale();
        String translatedMessage = messageSource.getMessage("error.500.default-msg", null, locale);
        model.addAttribute(ViewConstants.MESSAGE, translatedMessage);
        return PageNameConstants.ERROR_500;
    }
}
