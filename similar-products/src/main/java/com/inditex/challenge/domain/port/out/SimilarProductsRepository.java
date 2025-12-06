package com.inditex.challenge.domain.port.out;

import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;

import java.util.Set;

public interface SimilarProductsRepository {
    SimilarProductsId findSimilarIds(ProductId id);
}
