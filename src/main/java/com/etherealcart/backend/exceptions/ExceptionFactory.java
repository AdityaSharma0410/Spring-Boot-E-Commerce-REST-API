package com.etherealcart.backend.exceptions;

public class ExceptionFactory {

    public static InvalidUserIdException invalidUserId(String rawId) {
        return new InvalidUserIdException("Invalid ID format: " + rawId);
    }

    public static UserNotFoundException userNotFound(Long id) {
        return new UserNotFoundException(id);
    }

    public static DuplicateEmailException duplicateEmail(String email) {
        return new DuplicateEmailException("Email already exists: " + email);
    }

    public static MissingRequiredFieldsException missingFields(String message) {
        return new MissingRequiredFieldsException(message);
    }

    public static InvalidEmailFormatException invalidEmailFormat(String email) {
        return new InvalidEmailFormatException(email);
    }
}
