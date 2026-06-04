package com.fitness.dto.user_dto;

import com.fitness.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UserUpdateDto {

    @NotNull(message = "id is required")
    private Long id;

    @NotBlank(message = "first name is required")
    @Size(max = 50, message = "first name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(max = 50, message = "last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "email must not exceed 100 characters")
    private String email;

    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "the password must contain at least one lowercase letter, one uppercase letter and one number")
    private String password;

    @NotNull(message = "role is required")
    private UserRole role;

    public UserUpdateDto() { }

    public UserUpdateDto(Long id, String firstName, String lastName, String email, String password, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserUpdateDto that = (UserUpdateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, role);
    }
}
