package com.inditex.challenge.application.usecase.it;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.in.GetSimilarProductsIdUseCase;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//@ActiveProfiles("test")
public class GetSimilarProductsIdUseCaseIT {

    @Autowired
    private GetSimilarProductsIdUseCase useCase;
    @MockitoBean
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
