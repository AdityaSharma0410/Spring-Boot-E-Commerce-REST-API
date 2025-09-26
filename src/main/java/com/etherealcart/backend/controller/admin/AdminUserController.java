package com.etherealcart.backend.controller.admin;

import com.etherealcart.backend.dto.UpdateUserRoleRequest;
import com.etherealcart.backend.dto.UserResponseDTO;
import com.etherealcart.backend.dto.UserUpdateRequest;
import com.etherealcart.backend.mapper.UserMapper;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> list() {
        List<UserResponseDTO> users = userService.getAllUsers()
                .stream().map(UserMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> get(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @RequestBody UserUpdateRequest request) {
        User updated = userService.updateUser(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateRole(@PathVariable Long id,
                                                      @RequestBody UpdateUserRoleRequest req) {
        User existing = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        if (req.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
        existing.setRole(req.getRole());
        User saved = userService.updateUser(id, existing);
        return ResponseEntity.ok(UserMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
