package com.inditex.challenge.domain.exception;

public class ProductInvalidFieldException extends RuntimeException {
    public ProductInvalidFieldException(String description) {
        super(description);
    }
}
