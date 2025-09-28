package com.etherealcart.backend.service;

import com.etherealcart.backend.dto.UpdateUserRoleRequestDTO;
import com.etherealcart.backend.dto.UserUpdateRequestDTO;
import com.etherealcart.backend.exception.UserFieldsException;
import com.etherealcart.backend.mapper.UserMapper;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- Create User ---
    public User createUser(User user) {
        // Check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserFieldsException("Email '" + user.getEmail() + "' is already in use");
        }
        return userRepository.save(user);
    }

    // --- Get All Users ---
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --- Get User by ID ---
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserFieldsException("User not found with ID: " + id));
    }

    // --- Update User ---
    public User updateUser(Long id, UserUpdateRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserFieldsException("User not found with ID: " + id));
        UserMapper.updateEntity(user, request);
        return userRepository.save(user);
    }

    // --- Update Role ---
    public User updateUserRole(Long id, UpdateUserRoleRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserFieldsException("User not found with ID: " + id));
        UserMapper.updateRole(user, request);
        return userRepository.save(user);
    }

    // --- Delete User ---
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserFieldsException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
