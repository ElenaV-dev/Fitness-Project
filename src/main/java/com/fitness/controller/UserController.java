package com.fitness.controller;

import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/details";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "users/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("user") UserCreateDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        userService.save(dto);
        return "redirect:/users/list";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUpdateDtoById(id));
        return "users/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UserUpdateDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "users/update";
        }

        userService.update(dto);
        return "redirect:/users/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/users/list";
    }
}
