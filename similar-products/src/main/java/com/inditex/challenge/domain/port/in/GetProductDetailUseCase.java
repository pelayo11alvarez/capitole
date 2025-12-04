package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;

public interface GetProductDetailUseCase {
    Product execute(ProductId id);
}
