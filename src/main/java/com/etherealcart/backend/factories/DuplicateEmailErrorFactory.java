package com.etherealcart.backend.factories;

import com.etherealcart.backend.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DuplicateEmailErrorFactory implements ErrorResponseFactory {
    @Override
    public ApiError createError(Exception ex, HttpServletRequest request) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}