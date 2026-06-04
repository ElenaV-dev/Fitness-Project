package com.fitness.controller;

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
        model.addAttribute("users", userService.findAll());
        model.addAttribute("activeTab", "users");
        return "admin/users/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "admin/users/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("user") UserCreateDto dto, BindingResult bindingResult) {

        if (userValidator.emailExists(dto.getEmail())) {
            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    "User with this email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "admin/users/create";
        }

        userService.save(dto);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUpdateDtoById(id));
        return "admin/users/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("user") UserUpdateDto dto, BindingResult bindingResult) {

        if (userValidator.emailExistsForAnotherUser(dto.getEmail(), dto.getId())) {
            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    "User with this email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "admin/users/edit";
        }

        userService.update(dto);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
