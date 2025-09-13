package com.etherealcart.backend.factories;

import com.etherealcart.backend.dto.ApiError;
import com.etherealcart.backend.exceptions.InvalidEmailFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class InvalidEmailFormatErrorFactory implements ErrorResponseFactory {
    @Override
    public ApiError createError(Exception ex, HttpServletRequest request) {
        InvalidEmailFormatException e = (InvalidEmailFormatException) ex;
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                request.getRequestURI()
        );
    }
}
