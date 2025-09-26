package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.UserCreateRequest;
import com.etherealcart.backend.dto.UserResponseDTO;
import com.etherealcart.backend.dto.UserUpdateRequest;
import com.etherealcart.backend.model.User;

public class UserMapper {

    // Entity → Response DTO
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

    // CreateRequest → Entity
    public static User toEntity(UserCreateRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAvatar(request.getAvatar());
        user.setRole("user"); // default
        return user;
    }

    // UpdateRequest → update existing entity
    public static void updateEntity(User user, UserUpdateRequest request) {
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
}
