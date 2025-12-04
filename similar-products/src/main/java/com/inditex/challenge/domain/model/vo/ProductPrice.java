package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.VALUE_PRODUCT_PRICE_NEGATIVE_DESC;

public record ProductPrice(double value) {
    public ProductPrice {
        if (value < 0) {
            throw new ProductInvalidFieldException(VALUE_PRODUCT_PRICE_NEGATIVE_DESC);
        }
    }
}
