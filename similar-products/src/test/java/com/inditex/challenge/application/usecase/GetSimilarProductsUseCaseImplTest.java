package com.inditex.challenge.application.usecase;

import com.inditex.challenge.application.usecase.GetSimilarProductsUseCaseImpl;
import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSimilarProductsUseCaseImplTest {

    @InjectMocks
    private GetSimilarProductsUseCaseImpl useCase;
    @Mock
    private ProductDetailRepository repository;
    @Mock
    private ProductId productId;
    @Mock
    private Product product;

    @Test
    void givenSimilarProductsId_whenFindById_thenReturnSimilarProducts() {
        //given
        final var similarProductsId = new SimilarProductsId(Set.of(productId));
        when(repository.findById(productId)).thenReturn(Mono.just(product));
        //when
        final var result = useCase.execute(similarProductsId);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.products()),
                () -> assertFalse(result.products().isEmpty()),
                () -> assertTrue(result.products().contains(product))
        );
        verify(repository, times(1)).findById(productId);
    }

    @ParameterizedTest
    @MethodSource("similarProductsIdProvider")
    void givenNotValidSimilarProductsId_whenFindById_thenReturn(SimilarProductsId similarProductsId) {
        assertThrows((ProductGenericException.class), () -> useCase.execute(similarProductsId));
    }

    static Stream<Arguments> similarProductsIdProvider() {
        return Stream.of(
                null,
                Arguments.of(new SimilarProductsId(Set.of()))
        );
    }
}