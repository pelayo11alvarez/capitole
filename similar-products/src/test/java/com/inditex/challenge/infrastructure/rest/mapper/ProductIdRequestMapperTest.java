package com.inditex.challenge.infrastructure.rest.mapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductIdRequestMapperTest {

    @InjectMocks
    private ProductIdRequestMapperImpl mapper;

    @Test
    void toProductId() {
        final var id = Instancio.create(String.class);
        final var result = mapper.toProductId(id);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.value())
        );
    }
}