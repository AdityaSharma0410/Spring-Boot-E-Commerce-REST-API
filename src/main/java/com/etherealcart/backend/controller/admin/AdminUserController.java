package com.etherealcart.backend.controller.admin;

import com.etherealcart.backend.dto.UpdateUserRoleRequestDTO;
import com.etherealcart.backend.dto.UserResponseDTO;
import com.etherealcart.backend.dto.UserUpdateRequestDTO;
import com.etherealcart.backend.mapper.UserMapper;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    // --- List all users ---
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> dtos = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // --- Get user by ID ---
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable Long id) {
        User user = userService.getUserById(id); // UserFieldsException thrown if not found
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    // --- Update user (general info) ---
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO request) {
        User updated = userService.updateUser(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }

    // --- Update role only ---
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequestDTO request) {
        User updated = userService.updateUserRole(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }

    // --- Delete user ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
