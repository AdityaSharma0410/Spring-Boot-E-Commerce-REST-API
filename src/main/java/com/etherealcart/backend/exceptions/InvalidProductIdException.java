package com.etherealcart.backend.exceptions;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(String rawId) {
        super("Invalid product ID format: " + rawId);
    }
}
