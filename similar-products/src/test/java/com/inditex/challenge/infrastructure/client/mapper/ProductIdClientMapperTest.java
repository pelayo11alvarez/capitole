package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.identity.ProductId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductIdClientMapperTest {

    @InjectMocks
    private ProductIdClientMapperImpl mapper;

    @Test
    void givenId_whenMappToProductId_thenReturnProductId() {
        final var id = Instancio.create(long.class).toString();
        final var result = mapper.toProductId(id);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(Long.parseLong(id), result.value())
        );
    }

    @Test
    void givenIds_whenMappToProductIds_thenReturnSetProductId() {
        final var ids = Instancio.create(long[].class);
        final var result = mapper.toProductIds(ids);
        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(ids.length, result.size()),
                () -> assertTrue(result.contains(new ProductId(ids[0])))
        );
    }
}