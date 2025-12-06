package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import com.inditex.challenge.infrastructure.client.mapper.ProductClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDetailApiClientTest {

    @InjectMocks
    private ProductDetailApiClient productDetailApiClient;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private ProductClientMapper productClientMapper;
    @Mock
    private ProductClientResponseDTO dto;
    @Mock
    private Product product;
    @Mock
    private ProductId productId;

    @BeforeEach
    void setUp() {
        when(productId.value()).thenReturn(1L);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/product/{id}", 1L)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
    }

    @Test
    void givenValidId_whenFindProductById_thenReturn() {
        //given
        when(responseSpec.bodyToMono(ProductClientResponseDTO.class))
                .thenReturn(Mono.just(dto));
        when(productClientMapper.toDomain(dto)).thenReturn(product);

        //when
        final var result = productDetailApiClient.findById(productId);
        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
        //then
        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri("/product/{id}", 1L);
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, atLeastOnce()).onStatus(any(), any());
        verify(responseSpec, times(1)).bodyToMono(ProductClientResponseDTO.class);
        verify(productClientMapper, times(1)).toDomain(dto);
    }

    @Test
    void givenNotExistsId_whenFindById_thenThrowProductNotFoundException() {
        //given
        when(responseSpec.bodyToMono(ProductClientResponseDTO.class))
                .thenReturn(Mono.error(new ProductNotFoundException()));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(productId))
                .expectError(ProductNotFoundException.class)
                .verify();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri("/product/{id}", 1L);
        verify(requestHeadersSpec, times(1)).retrieve();
        verifyNoInteractions(productClientMapper);
    }

    @Test
    void givenProductId_whenFindById_thenThrowProductGenericException() {
        //given
        when(responseSpec.bodyToMono(ProductClientResponseDTO.class))
                .thenReturn(Mono.error(new ProductGenericException("")));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(productId))
                .expectError(ProductGenericException.class)
                .verify();

        verify(webClient, times(1)).get();
        verify(requestHeadersUriSpec, times(1)).uri("/product/{id}", 1L);
        verify(requestHeadersSpec, times(1)).retrieve();
        verifyNoInteractions(productClientMapper);
    }
}