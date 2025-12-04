package com.inditex.challenge.application;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.port.in.GetProductDetailUseCase;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSimilarProductsUseCaseImplTest {

    @InjectMocks
    private GetSimilarProductsUseCaseImpl useCase;
    @Mock
    private SimilarProductsRepository repository;
    @Mock
    private GetProductDetailUseCase getProductDetailUseCase;
    @Mock
    private ProductId productId;
    @Mock
    private Product product;

    @Test
    void execute() {
        when(repository.findSimilarIds(productId)).thenReturn(Set.of(productId));
        when(getProductDetailUseCase.execute(productId)).thenReturn(product);
        final var result = useCase.execute(productId);
        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.products()),
                () -> assertFalse(result.products().isEmpty()),
                () -> assertTrue(result.products().contains(product))
        );
        verify(repository, times(1)).findSimilarIds(productId);
        verify(getProductDetailUseCase, times(1)).execute(productId);
    }
}