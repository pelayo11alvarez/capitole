package com.inditex.challenge.infrastructure.rest.controller.it;

import com.inditex.challenge.application.workflow.SimilarProductsWorkFlow;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import com.inditex.challenge.infrastructure.rest.controller.ProductController;
import com.inditex.challenge.infrastructure.rest.mapper.ProductDetailRequestMapper;
import com.inditex.challenge.infrastructure.rest.mapper.ProductIdRequestMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerITTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SimilarProductsWorkFlow similarProductsWorkFlow;
    @MockitoBean
    private ProductIdRequestMapper productIdRequestMapper;
    @MockitoBean
    private ProductDetailRequestMapper productDetailRequestMapper;
    @Mock
    private SimilarProducts similarProducts;

    @Test
    void givenExistingProductId_whenGetProductSimilar_thenReturnOkWithBody() throws Exception {
        //given
        final var productId = 1L;
        final var domainId = new ProductId(productId);
        final var detail1 = Instancio.create(ProductDetail.class);
        final var detail2 = Instancio.create(ProductDetail.class);

        when(productIdRequestMapper.toProductId(String.valueOf(productId))).thenReturn(domainId);
        when(similarProductsWorkFlow.executeWorkFlow(domainId)).thenReturn(similarProducts);
        when(productDetailRequestMapper.toProductDetailSet(similarProducts))
                .thenReturn(Set.of(detail1, detail2));

        //when
        mockMvc.perform(get("/product/{productId}/similar", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        //then
        verify(productIdRequestMapper, times(1)).toProductId(String.valueOf(productId));
        verify(similarProductsWorkFlow, times(1)).executeWorkFlow(domainId);
        verify(productDetailRequestMapper, times(1)).toProductDetailSet(similarProducts);
    }

    //@Test
    void givenNonExistingProductId_whenGetProductSimilar_thenReturnNotFound() {
        //given
        final var productId = 999L;
        ProductId domainId = new ProductId(productId);

        when(productIdRequestMapper.toProductId(String.valueOf(productId))).thenReturn(domainId);
        when(similarProductsWorkFlow.executeWorkFlow(domainId))
                .thenThrow(new ProductNotFoundException());

        //when
        assertThrows((ProductNotFoundException.class),
                () -> mockMvc.perform(get("/product/{productId}/similar", productId))
                .andExpect(status().isNotFound()));

        //then
        verify(productIdRequestMapper, times(1)).toProductId(String.valueOf(productId));
        verify(similarProductsWorkFlow, times(1)).executeWorkFlow(domainId);
        verifyNoInteractions(productDetailRequestMapper);
    }
}
