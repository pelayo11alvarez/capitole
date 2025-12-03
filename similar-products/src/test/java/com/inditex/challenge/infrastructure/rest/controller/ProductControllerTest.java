package com.inditex.challenge.infrastructure.rest.controller;

import com.inditex.challenge.domain.model.vo.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import com.inditex.challenge.infrastructure.rest.mapper.ProductDetailRequestMapper;
import com.inditex.challenge.infrastructure.rest.mapper.ProductIdRequestMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController controller;
    @Mock
    private GetSimilarProductsUseCase getSimilarProductsUseCase;
    @Mock
    private ProductIdRequestMapper productIdRequestMapper;
    @Mock
    private ProductDetailRequestMapper productDetailRequestMapper;
    @Mock
    private ProductId productIdVO;
    @Mock
    private SimilarProducts similarProducts;
    @Mock
    private Set<ProductDetail> productDetailSet;

    @Test
    void getProductSimilar() {
        final var productId = Instancio.create(String.class);
        when(productIdRequestMapper.toProductId(productId)).thenReturn(productIdVO);
        when(getSimilarProductsUseCase.execute(productIdVO)).thenReturn(similarProducts);
        when(productDetailRequestMapper.toProductDetailSet(similarProducts)).thenReturn(productDetailSet);
        final var result = controller.getProductSimilar(productId);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(productDetailSet, result.getBody())
        );
        verify(productIdRequestMapper, times(1)).toProductId(productId);
        verify(getSimilarProductsUseCase, times(1)).execute(productIdVO);
        verify(productDetailRequestMapper, times(1)).toProductDetailSet(similarProducts);
    }
}