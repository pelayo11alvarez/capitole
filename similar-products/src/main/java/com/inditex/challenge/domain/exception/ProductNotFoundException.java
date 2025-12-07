package com.inditex.challenge.domain.exception;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.PRODUCT_NOT_FOUND_ERROR;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super(PRODUCT_NOT_FOUND_ERROR);
    }
}
