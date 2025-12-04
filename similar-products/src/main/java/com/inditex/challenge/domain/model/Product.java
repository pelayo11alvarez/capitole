package com.inditex.challenge.domain.model;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.ProductName;
import com.inditex.challenge.domain.model.vo.ProductPrice;

import java.util.Objects;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.*;

public class Product {
    private final ProductId id;
    private final ProductName name;
    private final ProductPrice price;
    private final boolean availability;

    public Product(ProductId id, ProductName name, ProductPrice price, boolean availability) {
        validateVOs(id, name, price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
    }

    private void validateVOs(ProductId id, ProductName name, ProductPrice price) {
        validateId(id);
        validateName(name);
        validatePrice(price);
    }

    private void validateId(ProductId id) {
        if (Objects.isNull(id)) {
            throw new ProductInvalidFieldException(PRODUCT_ID_NULL_DESC);
        }
    }

    private void validateName(ProductName name) {
        if (Objects.isNull(name)) {
            throw new ProductInvalidFieldException(PRODUCT_NAME_NULL_DESC);
        }
    }

    private void validatePrice(ProductPrice price) {
        if (Objects.isNull(price)) {
            throw new ProductInvalidFieldException(PRODUCT_PRICE_NULL_DESC);
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

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return availability;
    }
}
