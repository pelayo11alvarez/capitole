package com.inditex.challenge.application.workflow.impl;

import com.inditex.challenge.application.workflow.SimilarProductsWorkFlow;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.port.in.GetSimilarProductsIdUseCase;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import org.springframework.stereotype.Component;

@Component
public class SimilarProductsWorkFlowImpl implements SimilarProductsWorkFlow {

    private final GetSimilarProductsIdUseCase similarProductsIdUseCase;
    private final GetSimilarProductsUseCase similarProductsUseCase;

    public SimilarProductsWorkFlowImpl(GetSimilarProductsIdUseCase similarProductsIdUseCase,
                                       GetSimilarProductsUseCase similarProductsUseCase) {
        this.similarProductsIdUseCase = similarProductsIdUseCase;
        this.similarProductsUseCase = similarProductsUseCase;
    }

    @Override
    public SimilarProducts executeWorkFlow(ProductId productId) {
        final var similarIds = similarProductsIdUseCase.execute(productId);
        return similarProductsUseCase.execute(similarIds);
    }
}
