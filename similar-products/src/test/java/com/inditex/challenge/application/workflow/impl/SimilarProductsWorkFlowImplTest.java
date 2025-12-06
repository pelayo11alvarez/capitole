package com.inditex.challenge.application.workflow.impl;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsIdUseCase;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsWorkFlowImplTest {

    @InjectMocks
    private SimilarProductsWorkFlowImpl workFlow;
    @Mock
    private GetSimilarProductsIdUseCase getSimilarProductsIdUseCase;
    @Mock
    private GetSimilarProductsUseCase similarProductsUseCase;
    @Mock
    private ProductId productId;
    @Mock
    private SimilarProductsId similarProductsId;
    @Mock
    private SimilarProducts similarProducts;
    @Test
    void givenProductId_whenExecuteWorkFlow_thenReturnSimilarProducts() {
        //given
        when(getSimilarProductsIdUseCase.execute(productId)).thenReturn(similarProductsId);
        when(similarProductsUseCase.execute(similarProductsId)).thenReturn(similarProducts);
        //when /then
        assertNotNull(workFlow.executeWorkFlow(productId));
        verify(getSimilarProductsIdUseCase, times(1)).execute(productId);
        verify(similarProductsUseCase, times(1)).execute(similarProductsId);
    }
}