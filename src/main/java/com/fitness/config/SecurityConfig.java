package com.fitness.config;

import com.fitness.model.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registration", "/errors/404", "/errors/500").permitAll()
                        .requestMatchers("/account/**").hasAnyAuthority(UserRole.CLIENT.name(), UserRole.ADMIN.name())
                        .requestMatchers("/trainer/**").hasAnyAuthority(UserRole.TRAINER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {

            String role = authentication.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            if (UserRole.ADMIN.name().equals(role)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else if (UserRole.TRAINER.name().equals(role)) {
                response.sendRedirect(request.getContextPath() + "/trainer");
            } else {
                response.sendRedirect(request.getContextPath() + "/account");
            }
        };
    }
}
