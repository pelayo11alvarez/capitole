package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.infrastructure.client.mapper.SimilarProductsIdMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsApiClientTest {

    private static final String PRODUCT_DETAIL_SIMULADO_URL = "/product/{id}/similarids";
    @InjectMocks
    private SimilarProductsApiClient similarProductsApiClient;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private SimilarProductsIdMapper similarProductsIdMapper;
    @Mock
    private ProductId productId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(similarProductsApiClient, "similarProductSimuladoUrl", "/product/{id}/similarids");
        when(productId.value()).thenReturn(1L);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(PRODUCT_DETAIL_SIMULADO_URL, productId.value()))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    }

    @Test
    void givenProductId_whenFindSimilarIds_thenReturn() {
        //given
        final var responseIds = new long[]{111, 222, 333};
        final var expectedSet = Set.of(
                new ProductId(111),
                new ProductId(222),
                new ProductId(333)
        );
        final var similarProducts = new SimilarProductsId(expectedSet);
        when(responseSpec.bodyToMono(long[].class))
                .thenReturn(Mono.just(responseIds));
        when(similarProductsIdMapper.toSimilarProductsId(responseIds)).thenReturn(similarProducts);
        //when / then
        assertEquals(similarProducts, similarProductsApiClient.findSimilarIds(productId));
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1))
                .uri(PRODUCT_DETAIL_SIMULADO_URL, productId.value());
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(similarProductsIdMapper, times(1)).toSimilarProductsId(responseIds);
    }

    @Test
    void givenProductId_whenFindSimilarIds_thenThrowProductNotFoundException() {
        //given
        when(responseSpec.bodyToMono(long[].class))
                .thenReturn(Mono.error(new ProductNotFoundException()));
        //when /then
        assertThrows((ProductNotFoundException.class), () -> similarProductsApiClient.findSimilarIds(productId));
        verify(webClient, times(1)).get();
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1))
                .uri(PRODUCT_DETAIL_SIMULADO_URL, productId.value());
        verify(requestHeadersSpec, times(1)).retrieve();
        verifyNoInteractions(similarProductsIdMapper);
    }

    @Test
    void givenProductId_whenFindSimilarIds_thenThrowProductGenericException() {
        //given
        when(responseSpec.bodyToMono(long[].class))
                .thenReturn(Mono.error(new ProductGenericException("")));
        //when /then
        assertThrows((ProductGenericException.class), () -> similarProductsApiClient.findSimilarIds(productId));
        verify(webClient, times(1)).get();
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1))
                .uri(PRODUCT_DETAIL_SIMULADO_URL, productId.value());
        verify(requestHeadersSpec, times(1)).retrieve();
        verifyNoInteractions(similarProductsIdMapper);
    }
}