package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;

import java.util.List;

public interface GetSimilarProductsUseCase {
    default SimilarProducts execute(ProductId id) {return null;};
    SimilarProducts execute(SimilarProductsId similarProductsId);
}
