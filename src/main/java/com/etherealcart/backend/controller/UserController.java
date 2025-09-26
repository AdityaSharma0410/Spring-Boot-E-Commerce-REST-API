package com.etherealcart.backend.controller;

import com.etherealcart.backend.dto.UserCreateRequest;
import com.etherealcart.backend.dto.UserResponseDTO;
import com.etherealcart.backend.dto.UserUpdateRequest;
import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.exceptions.UserNotFoundException;
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
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateRequest request) {
        User user = UserMapper.toEntity(request);
        User saved = userService.createUser(user);
        return ResponseEntity.ok(UserMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
                .stream().map(UserMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        try {
            Long userId = Long.parseLong(id);
            Optional<User> user = userService.getUserById(userId);
            return user.map(u -> ResponseEntity.ok(UserMapper.toDTO(u)))
                    .orElseThrow(() -> ExceptionFactory.userNotFound(userId));
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidUserId(id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id,
                                                      @RequestBody UserUpdateRequest request) {
        try {
            Long userId = Long.parseLong(id);
            User updated = userService.updateUser(userId, request);
            return ResponseEntity.ok(UserMapper.toDTO(updated));
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
