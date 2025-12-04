package com.inditex.challenge.domain.exception;

public class ProductGenericException extends RuntimeException {
    public ProductGenericException(String description) {
        super(description);
    }
}
