package com.inditex.challenge.domain.port.out;

import com.inditex.challenge.domain.model.vo.ProductId;

import java.util.List;

public interface SimilarProductsRepository {
    List<ProductId> findSimilarIds(ProductId id);
}
