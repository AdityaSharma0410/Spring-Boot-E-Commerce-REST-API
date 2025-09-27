package com.etherealcart.backend.exceptions;

public class InvalidProductPriceException extends RuntimeException {

    public InvalidProductPriceException(Double price) {
        super("Invalid product price: " + price + ". Price must be greater than 0.");
    }

    public InvalidProductPriceException(String message) {
        super(message);
    }
}
