package com.inditex.challenge.application.usecase;

import com.inditex.challenge.domain.exception.ProductInvalidFieldException;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.SIMILAR_PRODUCTS_NULL_DESC;

@Service
public class GetSimilarProductsUseCaseImpl implements GetSimilarProductsUseCase {
    private final ProductDetailRepository repository;

    public GetSimilarProductsUseCaseImpl(ProductDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public SimilarProducts execute(SimilarProductsId similarProductsId) {
        validateSimilarProductsId(similarProductsId);
        final var products = Flux.fromIterable(similarProductsId.values())
                                .flatMap(repository::findById)
                                .collect(Collectors.toSet())
                                .block();
        return new SimilarProducts(products);
    }

    private void validateSimilarProductsId(SimilarProductsId similarProductsId) {
        if (Objects.isNull(similarProductsId) || similarProductsId.values().isEmpty()) {
            throw new ProductInvalidFieldException(SIMILAR_PRODUCTS_NULL_DESC);
        }
    }
}
