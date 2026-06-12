package com.fitness.dto.user_dto;

import com.fitness.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UserUpdateDto {

    @NotNull(message = "{user.id.required}")
    private Long id;

    @NotBlank(message = "{user.firstname.required}")
    @Size(max = 50, message = "{user.firstname.size}")
    private String firstName;

    @NotBlank(message = "{user.lastname.required}")
    @Size(max = 50, message = "{user.lastname.size}")
    private String lastName;

    @NotBlank(message = "{user.email.required}")
    @Email(message = "{user.email.valid}")
    @Size(max = 100, message = "{user.email.size}")
    private String email;

    @Size(min = 8, message = "{user.password.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "{user.password.pattern}")
    private String password;

    @NotNull(message = "{user.role.required}")
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
