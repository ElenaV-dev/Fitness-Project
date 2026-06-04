package com.fitness.controller;

import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserRegisterDto;
import com.fitness.service.interfaces.UserService;
import com.fitness.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizationController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserDetailsService userDetailsService;

    public AuthorizationController(
            UserService userService,
            UserValidator userValidator,
            UserDetailsService userDetailsService) {

        this.userService = userService;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new UserCreateDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserRegisterDto dto, BindingResult bindingResult,
                               HttpServletRequest request) {

        if (userValidator.emailExists(dto.getEmail())) {
            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    "User with this email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.registration(dto);

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession(true)
                .setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

        return "redirect:/account/workout-types";
    }
}
