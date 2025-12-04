package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductNameTest {

    @ParameterizedTest
    @MethodSource("namesProvider")
    void givenNotValidValue_whenCrateProductName_thenThrow(String name) {
        assertThrows((ProductInvalidFieldException.class),
                () -> new ProductName(name)
        );
    }

    @Test
    void givenValidValue_whenCrateProductName_thenNotThrow() {
        assertDoesNotThrow(() -> new ProductName(Instancio.create(String.class)));
    }

    static Stream<Arguments> namesProvider() {
        return Stream.of(
                null,
                Arguments.of("")
        );
    }
}