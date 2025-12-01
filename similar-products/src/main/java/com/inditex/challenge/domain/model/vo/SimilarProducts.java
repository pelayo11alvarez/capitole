package com.inditex.challenge.domain.model.vo;

import com.inditex.challenge.domain.model.Product;

import java.util.List;

public class SimilarProducts {
    private final List<Product> products;

    public SimilarProducts(List<Product> products) {
        this.products = List.copyOf(products);
    }

    public List<Product> getProducts() {
        return products;
    }
}
