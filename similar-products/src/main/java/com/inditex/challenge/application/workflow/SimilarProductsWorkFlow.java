package com.inditex.challenge.application.workflow;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;

public interface SimilarProductsWorkFlow {
    SimilarProducts executeWorkFlow(ProductId productId);
}
