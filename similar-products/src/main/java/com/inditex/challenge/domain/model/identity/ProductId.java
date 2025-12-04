package com.inditex.challenge.domain.model.identity;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;

import java.util.Objects;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.ID_PRODUCT_ID_NULL_DESC;

public record ProductId(String value) {
    public ProductId {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ProductInvalidFieldException(ID_PRODUCT_ID_NULL_DESC);
        }
    }
}
