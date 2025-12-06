package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;

public interface GetSimilarProductsIdUseCase {
    SimilarProductsId execute(ProductId productId);
}
