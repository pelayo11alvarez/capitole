package com.inditex.challenge.domain.port.in;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.vo.ProductId;

public interface GetProductDetailUseCase {
    Product execute(ProductId id);
}
