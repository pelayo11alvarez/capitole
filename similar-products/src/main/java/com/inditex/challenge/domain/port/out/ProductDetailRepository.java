package com.inditex.challenge.domain.port.out;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;

import java.util.Optional;

public interface ProductDetailRepository {
    Optional<Product> findById(ProductId id);
}
