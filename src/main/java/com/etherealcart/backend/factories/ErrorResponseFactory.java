package com.etherealcart.backend.factories;

import com.etherealcart.backend.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;

public interface ErrorResponseFactory {
    ApiError createError(Exception ex, HttpServletRequest request);
}
