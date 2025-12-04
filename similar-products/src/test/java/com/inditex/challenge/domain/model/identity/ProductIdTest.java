package com.inditex.challenge.domain.model.identity;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIdTest {

    @ParameterizedTest
    @MethodSource("idsProvider")
    void createInvalidProductId(String id) {
        assertThrows((ProductInvalidFieldException.class),
                () -> new ProductId(id));
    }

    @Test
    void createValidProductId() {
        assertDoesNotThrow(() -> new ProductId(Instancio.create(String.class)));
    }

    static Stream<Arguments> idsProvider() {
        return Stream.of(
                null,
                Arguments.of("")
        );
    }
}