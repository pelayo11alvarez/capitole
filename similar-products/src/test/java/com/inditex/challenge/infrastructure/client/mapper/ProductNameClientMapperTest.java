package com.inditex.challenge.infrastructure.client.mapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductNameClientMapperTest {

    @InjectMocks
    private ProductNameClientMapperImpl mapper;

    @Test
    void givenValidName_whenMappProductName_thenReturn() {
        //given
        final var name = Instancio.create(String.class);
        //when
        final var result = mapper.toProductName(name);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(name, result.value())
        );
    }
}