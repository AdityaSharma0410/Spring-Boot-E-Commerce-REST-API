package com.etherealcart.backend.exceptions;

public class InvalidCategoryIdException extends RuntimeException {
    public InvalidCategoryIdException(String message) {
        super(message);
    }
}
