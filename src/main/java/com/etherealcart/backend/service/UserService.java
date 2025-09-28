package com.etherealcart.backend.service;

import com.etherealcart.backend.dto.UpdateUserRoleRequestDTO;
import com.etherealcart.backend.dto.UserUpdateRequestDTO;
import com.etherealcart.backend.mapper.UserMapper;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // --- Create User ---
    public User createUser(User user) {
        if (user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Email, first name, last name, and password are required");
        }
        return userRepository.save(user);
    }

    // --- Get All Users ---
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --- Get User by ID ---
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // --- Update User ---
    public User updateUser(Long id, UserUpdateRequestDTO request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        User user = existingUser.get();
        UserMapper.updateEntity(user, request);
        return userRepository.save(user);
    }

    // --- Update Role ---
    public User updateUserRole(Long id, UpdateUserRoleRequestDTO request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        User user = existingUser.get();
        UserMapper.updateRole(user, request);
        return userRepository.save(user);
    }

    // --- Delete User ---
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
