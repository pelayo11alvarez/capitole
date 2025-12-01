package com.inditex.challenge.infrastructure.rest.controller;

import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.infrastructure.rest.api.ProductApi;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ProductController implements ProductApi {
    private final GetSimilarProductsUseCase getSimilarProductsUseCase;

    public ProductController(GetSimilarProductsUseCase getSimilarProductsUseCase) {
        this.getSimilarProductsUseCase = getSimilarProductsUseCase;
    }

    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        return null;
    }
}
