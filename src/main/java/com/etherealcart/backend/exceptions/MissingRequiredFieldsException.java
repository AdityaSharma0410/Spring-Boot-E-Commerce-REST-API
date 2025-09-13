package com.etherealcart.backend.exceptions;

public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String message) {
        super(message);
    }
}