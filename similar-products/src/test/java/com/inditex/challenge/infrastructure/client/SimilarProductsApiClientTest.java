package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.infrastructure.client.mapper.ProductIdClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimilarProductsApiClientTest {

    private SimilarProductsApiClient similarProductsApiClient;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;
    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private ProductIdClientMapper productIdClientMapper;
    @Mock
    private ProductId productId;

    @BeforeEach
    void setUp() {
        when(webClient.mutate()).thenReturn(webClientBuilder);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        similarProductsApiClient = new SimilarProductsApiClient(webClient, productIdClientMapper);
    }

    @Test
    void givenProductId_whenFindSimilarIds_thenReturn() {
        //given
        final var responseIds = new String[]{"111", "222", "333"};
        final var expectedSet = Set.of(
                new ProductId("111"),
                new ProductId("222"),
                new ProductId("333")
        );
        when(webClient
                .get()
                .uri("/product/{id}/similarids", productId.value())
                .retrieve()
                .bodyToMono(String[].class)
                .block()
        ).thenReturn(responseIds);
        when(productIdClientMapper.toProductIds(responseIds)).thenReturn(expectedSet);
        //when
        Set<ProductId> result = similarProductsApiClient.findSimilarIds(productId);
        //then
        assertEquals(expectedSet, result);
        verify(webClient, times(2)).get();
        verify(productIdClientMapper, times(1)).toProductIds(responseIds);
    }

    @Test
    void givenProductId_whenFindSimilarIds_thenThrowProductNotFoundException() {
        //given
        final var responseIds = new String[]{"111", "222", "333"};
        final var expectedSet = Set.of(
                new ProductId("111"),
                new ProductId("222"),
                new ProductId("333")
        );
        when(webClient
                .get()
                .uri("/product/{id}/similarids", productId.value())
                .retrieve()
                .bodyToMono(String[].class)
                .block()
        ).thenThrow(
                WebClientResponseException.create(
                        404,
                        "Not Found",
                        null,
                        null,
                        null
                )
        );
        //when /then
        assertThrows((ProductNotFoundException.class), () -> similarProductsApiClient.findSimilarIds(productId));
        verify(webClient, times(2)).get();
        verify(productIdClientMapper, times(0)).toProductIds(responseIds);
    }
}