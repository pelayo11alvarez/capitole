package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import com.inditex.challenge.infrastructure.client.mapper.ProductClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDetailApiClientTest {

    private ProductDetailApiClient productDetailApiClient;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;
    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private  ProductClientMapper productClientMapper;
    @Mock
    private Product product;
    @Mock
    private ProductId productId;
    @Mock
    private ProductClientResponseDTO dto;

    @BeforeEach
    void setUp() {
        when(webClient.mutate()).thenReturn(webClientBuilder);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        productDetailApiClient = new ProductDetailApiClient(webClient, productClientMapper);
    }

    @Test
    void givenValidId_whenFindProductById_thenReturn() {
        //given
        when(webClient
                .get()
                .uri("/product/{id}", productId.value())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .bodyToMono(ProductClientResponseDTO.class)
        ).thenReturn(Mono.just(dto));
        when(productClientMapper.toDomain(dto)).thenReturn(product);
        //when
        final var result = productDetailApiClient.findById(productId);
        //then
        assertTrue(result.isPresent());
        assertSame(product, result.get());
        verify(webClient, times(2)).get();
        verify(productClientMapper, times(1)).toDomain(dto);
    }

    @Test
    void givenNotValidId_whenFindProductById_thenThrowsProductNotFoundException() {
        //given
        when(webClient
                .get()
                .uri("/product/{id}", productId.value())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .bodyToMono(ProductClientResponseDTO.class)
        ).thenThrow(
                WebClientResponseException.create(
                        404,
                        "Not Found",
                        null,
                        null,
                        null
                )
        );
        //when
        assertThrows(ProductNotFoundException.class, () -> productDetailApiClient.findById(productId));
        //then
        verify(productClientMapper, times(0)).toDomain(any());
    }
}