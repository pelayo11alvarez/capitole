package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductPriceTest {

    @ParameterizedTest
    @MethodSource("priceProvider")
    void givenNegativeValue_whenCrateProductPrice_thenThrow(double price) {
        assertThrows((ProductInvalidFieldException.class),
                () -> new ProductPrice(price)
        );
    }

    @Test
    void givenValidValue_whenCrateProductPrice_thenNotThrow() {
        assertDoesNotThrow(() -> new ProductPrice(1));
    }

    static Stream<Arguments> priceProvider() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(0)
        );
    }
}