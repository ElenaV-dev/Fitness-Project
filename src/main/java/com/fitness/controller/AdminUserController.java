package com.fitness.controller;

import com.fitness.constants.*;
import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.service.interfaces.UserService;
import com.fitness.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public AdminUserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute(ViewConstants.USERS, userService.findAll());
        model.addAttribute(ViewConstants.ACTIVE_TAB, Tabs.USERS);
        return PageNameConstants.ADMIN_USERS_LIST;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute(ViewConstants.USER, new UserCreateDto());
        return PageNameConstants.ADMIN_USERS_CREATE;
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("user") UserCreateDto dto, BindingResult bindingResult) {

        if (userValidator.emailExists(dto.getEmail())) {
            bindingResult.rejectValue(
                    FieldConstants.EMAIL,
                    ValidationConstants.EMAIL_EXISTS);
        }

        if (bindingResult.hasErrors()) {
            return PageNameConstants.ADMIN_USERS_CREATE;
        }

        userService.save(dto);
        return UriConstants.REDIRECT_ADMIN_USERS;
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute(ViewConstants.USER, userService.findUpdateDtoById(id));
        return PageNameConstants.ADMIN_USERS_EDIT;
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("user") UserUpdateDto dto, BindingResult bindingResult) {

        if (userValidator.emailExistsForAnotherUser(dto.getEmail(), dto.getId())) {
            bindingResult.rejectValue(
                    FieldConstants.EMAIL,
                    ValidationConstants.EMAIL_EXISTS);
        }

        if (bindingResult.hasErrors()) {
            return PageNameConstants.ADMIN_USERS_EDIT;
        }

        userService.update(dto);
        return UriConstants.REDIRECT_ADMIN_USERS;
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return UriConstants.REDIRECT_ADMIN_USERS;
    }
}
