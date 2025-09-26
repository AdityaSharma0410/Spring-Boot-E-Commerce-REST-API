package com.etherealcart.backend.service;

import com.etherealcart.backend.dto.UserUpdateRequest;
import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.repository.UserRepository;
import com.etherealcart.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (user.getEmail() == null || user.getFirstName() == null
                || user.getLastName() == null || user.getPassword() == null) {
            throw ExceptionFactory.missingFields("Email, first name, last name, and password are required");
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw ExceptionFactory.invalidEmailFormat(user.getEmail());
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw ExceptionFactory.duplicateEmail(user.getEmail());
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Overload: Update using DTO request
    public User updateUser(Long id, UserUpdateRequest request) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw ExceptionFactory.userNotFound(id);
        }

        User user = existingUser.get();
        UserMapper.updateEntity(user, request);
        return userRepository.save(user);
    }

    // Existing method still useful for admin role update
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw ExceptionFactory.userNotFound(id);
        }

        User user = existingUser.get();
        user.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : user.getEmail());
        user.setFirstName(updatedUser.getFirstName() != null ? updatedUser.getFirstName() : user.getFirstName());
        user.setLastName(updatedUser.getLastName() != null ? updatedUser.getLastName() : user.getLastName());

        if (updatedUser.getPassword() != null) {
            user.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getAvatar() != null) {
            user.setAvatar(updatedUser.getAvatar());
        }
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
