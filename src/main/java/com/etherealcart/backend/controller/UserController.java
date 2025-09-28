package com.etherealcart.backend.controller;

import com.etherealcart.backend.dto.*;
import com.etherealcart.backend.mapper.UserMapper;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- Create User ---
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO request) {
        User user = UserMapper.toEntity(request);
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }

    // --- Get All Users ---
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> dtos = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // --- Get User by ID ---
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(UserMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- Update User ---
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UserUpdateRequestDTO request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
    }

    // --- Update Role ---
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(@PathVariable Long id,@Valid @RequestBody UpdateUserRoleRequestDTO request) {
        User updatedUser = userService.updateUserRole(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
    }

    // --- Delete User ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
