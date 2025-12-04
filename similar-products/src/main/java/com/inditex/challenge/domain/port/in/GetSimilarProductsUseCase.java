package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;

public interface GetSimilarProductsUseCase {
    SimilarProducts execute(ProductId id);
}
