package com.inditex.challenge.application.workflow.impl.it;

import com.inditex.challenge.application.workflow.SimilarProductsWorkFlow;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsIdUseCase;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class SimilarProductsWorkFlowImplIT {

    @Autowired
    private SimilarProductsWorkFlow workFlow;
    @MockitoBean
    private GetSimilarProductsIdUseCase getSimilarProductsIdUseCase;
    @MockitoBean
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
        //when / then
        assertNotNull(workFlow.executeWorkFlow(productId));
        verify(getSimilarProductsIdUseCase, times(1)).execute(productId);
        verify(similarProductsUseCase, times(1)).execute(similarProductsId);
    }
}
