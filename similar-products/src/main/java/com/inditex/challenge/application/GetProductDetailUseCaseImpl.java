package com.inditex.challenge.application;

import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.port.in.GetProductDetailUseCase;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProductDetailUseCaseImpl implements GetProductDetailUseCase {
    private final ProductDetailRepository repository;

    public GetProductDetailUseCaseImpl(ProductDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product execute(ProductId id) {
        return repository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
