package com.inditex.challenge.infrastructure.rest.controller;

import com.inditex.challenge.application.workflow.SimilarProductsWorkFlow;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import com.inditex.challenge.infrastructure.rest.controller.ProductController;
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
    private SimilarProductsWorkFlow similarProductsWorkFlow;
    @Mock
    private ProductIdRequestMapper productIdRequestMapper;
    @Mock
    private ProductDetailRequestMapper productDetailRequestMapper;
    @Mock
    private ProductId productIdentity;
    @Mock
    private SimilarProducts similarProducts;
    @Mock
    private Set<ProductDetail> productDetailSet;

    @Test
    void givenProductId_whenGetProductSimilar_thenReturn() {
        //given
        final var productId = Instancio.create(String.class);
        when(productIdRequestMapper.toProductId(productId)).thenReturn(productIdentity);
        when(similarProductsWorkFlow.executeWorkFlow(productIdentity)).thenReturn(similarProducts);
        when(productDetailRequestMapper.toProductDetailSet(similarProducts)).thenReturn(productDetailSet);
        //when
        final var result = controller.getProductSimilar(productId);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, result.getStatusCode()),
                () -> assertEquals(productDetailSet, result.getBody())
        );
        verify(productIdRequestMapper, times(1)).toProductId(productId);
        verify(similarProductsWorkFlow, times(1)).executeWorkFlow(productIdentity);
        verify(productDetailRequestMapper, times(1)).toProductDetailSet(similarProducts);
    }
}