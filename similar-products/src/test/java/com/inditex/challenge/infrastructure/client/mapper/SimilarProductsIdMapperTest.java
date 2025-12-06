package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.identity.ProductId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsIdMapperTest {

    @InjectMocks
    private SimilarProductsIdMapperImpl mapper;
    @Mock
    private ProductIdClientMapper productIdClientMapper;

    @Test
    void givenIds_whenMappToSimilarProductsId_thenSimilarProductsId() {
        //given
        final var ids = Instancio.create(long[].class);
        final var setIds = Instancio.ofSet(ProductId.class).size(1).create();
        when(productIdClientMapper.toProductIds(ids)).thenReturn(setIds);
        //when
        final var result = mapper.toSimilarProductsId(ids);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.values().isEmpty()),
                () -> assertEquals(1, result.values().size())
        );
        verify(productIdClientMapper, times(1)).toProductIds(ids);
    }
}