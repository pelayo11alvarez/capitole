package com.inditex.challenge.application;

import com.inditex.challenge.domain.model.vo.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.port.in.GetProductDetailUseCase;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import org.springframework.stereotype.Service;

@Service
public class GetSimilarProductsUseCaseImpl implements GetSimilarProductsUseCase {
    private final SimilarProductsRepository repository;
    private final GetProductDetailUseCase getProductDetailUseCase;

    public GetSimilarProductsUseCaseImpl(SimilarProductsRepository repository,
                                         GetProductDetailUseCase getProductDetailUseCase) {
        this.repository = repository;
        this.getProductDetailUseCase = getProductDetailUseCase;
    }

    @Override
    public SimilarProducts execute(ProductId id) {
        final var ids = repository.findSimilarIds(id);
        final var products = ids.stream()
                .map(getProductDetailUseCase::execute)
                .toList();
        return new SimilarProducts(products);
    }
}
