package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.model.identity.ProductId;

import java.util.Set;

public record SimilarProductsId(Set<ProductId> values) {
}
