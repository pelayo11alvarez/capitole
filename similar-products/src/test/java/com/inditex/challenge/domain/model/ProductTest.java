package com.inditex.challenge.domain.model;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.ProductName;
import com.inditex.challenge.domain.model.vo.ProductPrice;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void givenInvalidProductId_whenCreateInvalidProduct_thenThrow() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new Product(
                        null,
                        Instancio.create(ProductName.class),
                        Instancio.create(ProductPrice.class),
                        Instancio.create(boolean.class)
                )
        );
    }

    @Test
    void givenInvalidProductName_whenCreateInvalidProduct_thenThrow() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new Product(
                        Instancio.create(ProductId.class),
                        null,
                        Instancio.create(ProductPrice.class),
                        Instancio.create(boolean.class)
                )
        );
    }

    @Test
    void givenInvalidProductPrice_whenCreateInvalidProduct_thenThrow() {
        assertThrows((ProductInvalidFieldException.class),
                () -> new Product(
                        Instancio.create(ProductId.class),
                        Instancio.create(ProductName.class),
                        null,
                        Instancio.create(boolean.class)
                )
        );
    }

    @Test
    void givenValidProduct_whenCreateValidProduct_thenNotThrow() {
        final var productId = new ProductId(Instancio.create(String.class));
        assertDoesNotThrow(() -> new Product(
                productId,
                Instancio.create(ProductName.class),
                Instancio.create(ProductPrice.class),
                Instancio.create(boolean.class)
        ));
    }
}