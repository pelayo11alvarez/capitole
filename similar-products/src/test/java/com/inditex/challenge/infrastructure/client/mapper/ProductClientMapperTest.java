package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductClientMapperTest {

    @InjectMocks
    private ProductClientMapperImpl mapper;

    @Test
    void toDomain() {
        final var dto = Instancio.of(ProductClientResponseDTO.class)
                .set(field(ProductClientResponseDTO::price), "12.34")
                .create();
        final var result = mapper.toDomain(dto);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(dto.id(), result.getId().value()),
                () -> assertEquals(dto.name(), result.getName()),
                () -> assertEquals(Double.parseDouble(dto.price()), result.getPrice()),
                () -> assertEquals(dto.availability(), result.isAvailable())
        );
    }
}