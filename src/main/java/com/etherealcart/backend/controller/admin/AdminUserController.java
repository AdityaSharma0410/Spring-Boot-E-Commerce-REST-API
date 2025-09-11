package com.etherealcart.backend.controller.admin;

import com.etherealcart.backend.dto.UpdateUserRoleRequest;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> list() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long id, @RequestBody UpdateUserRoleRequest req) {
        User existing = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        if (req.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
        existing.setRole(req.getRole());
        return ResponseEntity.ok(userService.updateUser(id, existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


