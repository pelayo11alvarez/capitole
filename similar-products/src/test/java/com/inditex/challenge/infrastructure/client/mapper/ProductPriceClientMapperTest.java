package com.inditex.challenge.infrastructure.client.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceClientMapperTest {

    @InjectMocks
    private ProductPriceClientMapperImpl mapper;

    @Test
    void givenValidPrice_whenMappProductPrice_thenReturn() {
        //given
        final var price = "1";
        //when
        final var result = mapper.toProductPrice(price);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.value())
        );
    }

    @Test
    void givenValidValue_whenParseToDouble_thenReturn() {
        //given
        final var price = "1";
        //when
        final var result = mapper.stringToDouble(price);
        //then
        assertEquals(1, result);
    }
}