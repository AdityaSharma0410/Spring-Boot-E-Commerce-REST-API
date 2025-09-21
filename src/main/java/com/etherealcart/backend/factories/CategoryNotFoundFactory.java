package com.etherealcart.backend.factories;

import com.etherealcart.backend.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CategoryNotFoundFactory implements ErrorResponseFactory {
    @Override
    public ApiError createError(Exception ex, HttpServletRequest request) {
        return new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage() != null ? ex.getMessage() : "Category not found",
                request.getRequestURI()
        );
    }
}
