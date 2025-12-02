package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.Product;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimilarProductsTest {

    @Test
    void createInvalidSimilarProducts() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new SimilarProducts(null));
    }

    @Test
    void createValidSimilarProducts() {
        final var products = Instancio.createSet(Product.class);
        assertDoesNotThrow(() -> new SimilarProducts(products));
    }
}