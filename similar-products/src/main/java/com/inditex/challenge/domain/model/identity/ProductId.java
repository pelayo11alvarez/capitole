package com.inditex.challenge.domain.model.identity;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.VALUE_PRODUCT_ID_NEGATIVE_DESC;

public record ProductId(long value) {
    public ProductId {
        if (value < 0) {
            throw new ProductInvalidFieldException(VALUE_PRODUCT_ID_NEGATIVE_DESC);
        }
    }
}
