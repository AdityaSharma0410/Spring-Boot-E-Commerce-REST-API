package com.etherealcart.backend.exceptions;

public class ExceptionFactory {

    // === USER exceptions ===
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

    // === PRODUCT exceptions ===
    public static InvalidProductIdException invalidProductId(String rawId) {
        return new InvalidProductIdException(rawId);
    }

    public static ProductNotFoundException productNotFound(Long id) {
        return new ProductNotFoundException(id);
    }

    public static MissingProductFieldsException missingProductFields(String message) {
        return new MissingProductFieldsException(message);
    }

    public static InvalidProductPriceException invalidProductPrice(Double price) {
        return new InvalidProductPriceException(price);
    }

    // 🔹 For missing fields
    public static MissingCategoryFieldsException missingCategoryFields(String fields) {
        return new MissingCategoryFieldsException(fields + " are required");
    }

    // 🔹 For category not found
    public static CategoryNotFoundException categoryNotFound(Long id) {
        return new CategoryNotFoundException("Category not found with ID: " + id);
    }

    public static CategoryNotFoundException categoryNotFound(String slug) {
        return new CategoryNotFoundException("Category not found with slug: " + slug);
    }

    // 🔹 For invalid ID
    public static InvalidCategoryIdException invalidCategoryId(String id) {
        return new InvalidCategoryIdException("Invalid category ID: " + id);
    }

    public static DuplicateCategorySlugException duplicateSlug(String slug) {
        return new DuplicateCategorySlugException(slug);
    }
}
