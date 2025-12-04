package com.inditex.challenge.domain.model.identity;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIdTest {

    @Test
    void givenNegativeValue_whenCreateInvalidProductId_thenThrowProductInvalidFieldException() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new ProductId(-1));
    }

    @Test
    void givenPositiveValue_whenCreateInvalidProductId_thenCreateProductId() {
        assertDoesNotThrow(() -> new ProductId(Instancio.create(Long.class)));
    }
}