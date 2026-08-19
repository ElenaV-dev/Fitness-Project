package com.fitness.dto.user_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserRegisterDto {

    @NotBlank(message = "{user.firstname.required}")
    @Size(max = 50, message = "{user.firstname.size}")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "{user.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{user.lastname.required}")
    @Size(max = 50, message = "{user.lastname.size}")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "{user.lastname.pattern}")
    private String lastName;

    @NotBlank(message = "{user.email.required}")
    @Email(message = "{user.email.valid}")
    @Size(max = 100, message = "{user.email.size}")
    private String email;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 8, message = "{user.password.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "{user.password.pattern}")
    private String password;

    public UserRegisterDto() {}

    public UserRegisterDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterDto that = (UserRegisterDto) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password);
    }
}
