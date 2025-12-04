package com.inditex.challenge.domain.port.out;

import com.inditex.challenge.domain.model.identity.ProductId;

import java.util.Set;

public interface SimilarProductsRepository {
    Set<ProductId> findSimilarIds(ProductId id);
}
