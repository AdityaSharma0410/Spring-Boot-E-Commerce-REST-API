package com.etherealcart.backend.exceptions;

import com.etherealcart.backend.dto.ApiError;
import com.etherealcart.backend.factoryregistry.ErrorResponseFactoryRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorResponseFactoryRegistry factoryRegistry;

    @Autowired
    public GlobalExceptionHandler(ErrorResponseFactoryRegistry factoryRegistry) {
        this.factoryRegistry = factoryRegistry;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ApiError errorResponse = factoryRegistry.createErrorResponse(ex, request);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
