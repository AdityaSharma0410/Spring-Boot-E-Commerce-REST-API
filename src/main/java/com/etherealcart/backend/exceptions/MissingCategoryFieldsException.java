package com.etherealcart.backend.exceptions;

public class MissingCategoryFieldsException extends RuntimeException {
    public MissingCategoryFieldsException(String message) {
        super(message);
    }
}
