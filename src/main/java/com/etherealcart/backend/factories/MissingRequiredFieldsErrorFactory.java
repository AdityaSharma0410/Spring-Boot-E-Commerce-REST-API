package com.etherealcart.backend.factories;

import com.etherealcart.backend.dto.ApiError;
import com.etherealcart.backend.exceptions.MissingRequiredFieldsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MissingRequiredFieldsErrorFactory implements ErrorResponseFactory {
    @Override
    public ApiError createError(Exception ex, HttpServletRequest request) {
        MissingRequiredFieldsException e = (MissingRequiredFieldsException) ex;
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                e.getMessage(),
                request.getRequestURI()
        );
    }
}
