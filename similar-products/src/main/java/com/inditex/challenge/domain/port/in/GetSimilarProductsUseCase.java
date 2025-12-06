package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;

public interface GetSimilarProductsUseCase {
    SimilarProducts execute(SimilarProductsId similarProductsId);
}
