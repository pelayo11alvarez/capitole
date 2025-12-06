package com.inditex.challenge.application.usecase;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsIdUseCase;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GetSimilarProductsIdUseCaseImpl implements GetSimilarProductsIdUseCase {
    private final SimilarProductsRepository repository;

    public GetSimilarProductsIdUseCaseImpl(SimilarProductsRepository repository) {
        this.repository = repository;
    }

    @Override
    public SimilarProductsId execute(ProductId productId) {
        validateProductId(productId);
        return repository.findSimilarIds(productId);
    }

    private void validateProductId(ProductId productId) {
        if (Objects.isNull(productId)) {
            throw new ProductGenericException("");
        }
    }
}
