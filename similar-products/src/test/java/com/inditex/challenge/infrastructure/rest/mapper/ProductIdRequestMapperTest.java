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
    void givenId_whenMapTtoProductId_thenReturnProductId() {
        //given
        final var id = Instancio.create(long.class);
        //when
        final var result = mapper.toProductId(id.toString());
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.value())
        );
    }
}