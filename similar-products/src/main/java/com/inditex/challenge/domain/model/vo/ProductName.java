package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;

import java.util.Objects;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.VALUE_PRODUCT_NAME_NULL_DESC;

public record ProductName(String value) {
    public ProductName {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ProductInvalidFieldException(VALUE_PRODUCT_NAME_NULL_DESC);
        }
    }
}
