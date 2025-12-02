package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.Product;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.SIMILAR_PRODUCTS_NULL_DESC;

public record SimilarProducts(Set<Product> products) {
    public SimilarProducts {
        if (Objects.isNull(products)) {
            throw new ProductInvalidFieldException(SIMILAR_PRODUCTS_NULL_DESC);
        }
        products = Collections.unmodifiableSet(new LinkedHashSet<>(products));
    }
}
