package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.*;
import com.etherealcart.backend.model.User;

public class UserMapper {

    // --- Entity -> ResponseDTO ---
    public static UserResponseDTO toDTO(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setAvatar(user.getAvatar());
        return dto;
    }

    // --- CreateRequestDTO -> Entity ---
    public static User toEntity(UserCreateRequestDTO request) {
        if (request == null) return null;

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAvatar(request.getAvatar());
        // role defaults to "user" from entity
        return user;
    }

    // --- UpdateRequestDTO -> Update existing Entity ---
    public static void updateEntity(User user, UserUpdateRequestDTO request) {
        if (user == null || request == null) return;

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
    }

    // --- UpdateUserRoleRequestDTO -> Update role only ---
    public static void updateRole(User user, UpdateUserRoleRequestDTO request) {
        if (user == null || request == null) return;
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
    }
}
