package com.inditex.challenge.application.usecase.it;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsUseCase;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GetSimilarProductsUseCaseIT {

    @Autowired
    private GetSimilarProductsUseCase getSimilarProductsUseCase;
    @MockitoBean
    private ProductDetailRepository repository;
    @Mock
    private ProductId productId;
    @Mock
    private Product product;
    private SimilarProductsId similarProductsId;

    @BeforeEach
    void setUp() {
        similarProductsId = new SimilarProductsId(Set.of(productId));
    }

    @Test
    void givenSimilarIds_whenExecute_thenReturnsSimilarProducts() {
        //given
        when(repository.findById(productId)).thenReturn(Mono.just(product));
        //when
        final var result = getSimilarProductsUseCase.execute(similarProductsId);
        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.products()),
                () -> assertFalse(result.products().isEmpty()),
                () -> assertTrue(result.products().contains(product))
        );
        verify(repository, times(1)).findById(productId);
    }

    @Test
    void givenNoSimilarIds_whenExecute_thenReturnsEmptySimilarProducts() {
        //given
        when(repository.findById(productId)).thenReturn(Mono.empty());
        //when
        final var result = getSimilarProductsUseCase.execute(similarProductsId);
        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.products()),
                () -> assertTrue(result.products().isEmpty())
        );
        verify(repository, times(1)).findById(productId);
    }
}
