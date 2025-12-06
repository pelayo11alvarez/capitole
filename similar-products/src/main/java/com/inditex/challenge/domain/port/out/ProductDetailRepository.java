package com.inditex.challenge.domain.port.out;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ProductDetailRepository {
    Mono<Product> findById(ProductId id);
}
