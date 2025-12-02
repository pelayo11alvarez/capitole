package com.inditex.challenge.domain.model;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.vo.ProductId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void createInvalidProduct() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new Product(
                        null,
                        Instancio.create(String.class),
                        Instancio.create(double.class),
                        Instancio.create(boolean.class)
                )
        );
    }

    @Test
    void createValidProduct() {
        final var productId = new ProductId(Instancio.create(String.class));
        assertDoesNotThrow(() -> new Product(
                productId,
                Instancio.create(String.class),
                Instancio.create(double.class),
                Instancio.create(boolean.class)
        ));
    }
}