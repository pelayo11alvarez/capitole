package com.inditex.challenge.application.usecase;

import com.inditex.challenge.application.usecase.GetSimilarProductsIdUseCaseImpl;
import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSimilarProductsIdUseCaseImplTest {

    @InjectMocks
    private GetSimilarProductsIdUseCaseImpl useCase;
    @Mock
    private SimilarProductsRepository repository;
    @Mock
    private ProductId productId;
    @Mock
    private SimilarProductsId similarProductsId;
    @Test
    void givenProductId_whenFindSimilarIds_thenReturnSimilarProductsId() {
        //given
        when(repository.findSimilarIds(productId)).thenReturn(similarProductsId);
        //when
        final var result = useCase.execute(productId);
        //then
        assertAll(
                () -> assertNotNull(result),
                () -> assertNotNull(result.values())
        );
        verify(repository, times(1)).findSimilarIds(productId);
    }

    @Test
    void givenNullProductId_whenFindSimilarIds_thenProductGenericException() {
        //given /when /then
        assertThrows((ProductGenericException.class), () -> useCase.execute(null));
        verifyNoInteractions(repository);
    }
}