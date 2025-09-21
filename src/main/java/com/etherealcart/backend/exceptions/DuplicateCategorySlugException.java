package com.etherealcart.backend.exceptions;

public class DuplicateCategorySlugException extends RuntimeException {
    public DuplicateCategorySlugException(String slug) {
        super("Category slug already exists: " + slug);
    }
}
