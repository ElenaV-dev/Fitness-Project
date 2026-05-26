package com.fitness.mapper;

import com.fitness.dto.user_dto.UserCreateDto;
import com.fitness.dto.user_dto.UserResponseDto;
import com.fitness.dto.user_dto.UserUpdateDto;
import com.fitness.model.User;
import com.fitness.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::toResponseDto)
                .toList();
    }

    public User createToEntity(UserCreateDto dto) {

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.CLIENT);

        return user;
    }

    public void updateEntity(UserUpdateDto dto, User user) {

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRole(dto.getRole());
    }
}
