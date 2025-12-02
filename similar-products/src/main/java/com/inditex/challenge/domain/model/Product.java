package com.inditex.challenge.domain.model;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.vo.ProductId;

import java.util.Objects;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.PRODUCT_ID_NULL_DESC;

public class Product {
    private final ProductId id;
    private final String name;
    private final double price;
    private final boolean availability;

    public Product(ProductId id, String name, double price, boolean availability) {
        validateId(id);
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    private void validateId(ProductId id) {
        if (Objects.isNull(id)) {
            throw new ProductInvalidFieldException(PRODUCT_ID_NULL_DESC);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }
}
