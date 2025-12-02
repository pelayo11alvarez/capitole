package com.inditex.challenge.infrastructure.rest.controller;

import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.infrastructure.rest.api.ProductApi;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import com.inditex.challenge.infrastructure.rest.mapper.ProductDetailRequestMapper;
import com.inditex.challenge.infrastructure.rest.mapper.ProductIdRequestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ProductController implements ProductApi {
    private final GetSimilarProductsUseCase getSimilarProductsUseCase;
    private final ProductIdRequestMapper productIdRequestMapper;
    private final ProductDetailRequestMapper productDetailRequestMapper;

    public ProductController(GetSimilarProductsUseCase getSimilarProductsUseCase,
                             ProductIdRequestMapper productIdRequestMapper,
                             ProductDetailRequestMapper productDetailRequestMapper) {
        this.getSimilarProductsUseCase = getSimilarProductsUseCase;
        this.productIdRequestMapper = productIdRequestMapper;
        this.productDetailRequestMapper = productDetailRequestMapper;
    }

    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        final var productIdVO = productIdRequestMapper.toProductId(productId);
        final var similarProducts = getSimilarProductsUseCase.execute(productIdVO);
        final var productDetails = productDetailRequestMapper.toProductDetailSet(similarProducts);
        return ResponseEntity.ok(productDetails);
    }
}
