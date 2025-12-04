package com.inditex.challenge.application;

import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductDetailUseCaseImplTest {

    @InjectMocks
    private GetProductDetailUseCaseImpl useCase;
    @Mock
    private ProductDetailRepository repository;
    @Mock
    private ProductId productId;
    @Mock
    private Product product;

    @Test
    void executeOk() {
        when(repository.findById(productId)).thenReturn(Optional.of(product));
        final var result = useCase.execute(productId);
        assertEquals(product, result);
        verify(repository, times(1)).findById(productId);
    }

    @Test
    void executeException() {
        when(repository.findById(productId)).thenReturn(Optional.empty());
        assertThrows((ProductNotFoundException.class), () -> useCase.execute(productId));
        verify(repository, times(1)).findById(productId);
    }
}