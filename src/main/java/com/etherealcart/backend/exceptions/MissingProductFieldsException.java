package com.etherealcart.backend.exceptions;

public class MissingProductFieldsException extends RuntimeException {
    public MissingProductFieldsException(String message) {
        super(message);
    }
}
