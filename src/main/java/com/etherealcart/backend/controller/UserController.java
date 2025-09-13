package com.etherealcart.backend.controller;

import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.exceptions.UserNotFoundException;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            Optional<User> user = userService.getUserById(userId);
            return user.map(ResponseEntity::ok)
                    .orElseThrow(() -> ExceptionFactory.userNotFound(userId));
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidUserId(id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            Long userId = Long.parseLong(id);
            return ResponseEntity.ok(userService.updateUser(userId, user));
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidUserId(id);
        } catch (UserNotFoundException ex) {
            throw ex; // handled globally
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);

            if (!userService.getUserById(userId).isPresent()) {
                throw ExceptionFactory.userNotFound(userId);
            }

            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidUserId(id);
        }
    }
}
