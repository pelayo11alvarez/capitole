package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.ProductName;
import com.inditex.challenge.domain.model.vo.ProductPrice;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductClientMapperTest {

    @InjectMocks
    private ProductClientMapperImpl mapper;
    @Mock
    private ProductIdClientMapper productIdClientMapper;
    @Mock
    private ProductNameClientMapper productNameClientMapper;
    @Mock
    private ProductPriceClientMapper productPriceClientMapper;

    @Test
    void givenDto_whenMappToDomain_thenReturnProduct() {
        //given
        final var dto = Instancio.of(ProductClientResponseDTO.class)
                .set(field(ProductClientResponseDTO::id), "1")
                .set(field(ProductClientResponseDTO::price), "12.34")
                .create();
        when(productIdClientMapper.toProductId(dto.id())).thenReturn(new ProductId(Long.parseLong(dto.id())));
        when(productNameClientMapper.toProductName(dto.name())).thenReturn(new ProductName(dto.name()));
        when(productPriceClientMapper.toProductPrice(dto.price())).thenReturn(new ProductPrice(Double.parseDouble(dto.price())));
        //when
        final var result = mapper.toDomain(dto);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(Long.parseLong(dto.id()), result.getId().value()),
                () -> assertEquals(dto.name(), result.getName().value()),
                () -> assertEquals(Double.parseDouble(dto.price()), result.getPrice().value()),
                () -> assertEquals(dto.availability(), result.isAvailable())
        );
        verify(productIdClientMapper, times(1)).toProductId(dto.id());
        verify(productNameClientMapper, times(1)).toProductName(dto.name());
        verify(productPriceClientMapper, times(1)).toProductPrice(dto.price());
    }
}