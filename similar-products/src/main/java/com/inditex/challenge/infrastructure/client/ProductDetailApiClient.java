package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import com.inditex.challenge.infrastructure.client.mapper.ProductClientMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ProductDetailApiClient implements ProductDetailRepository {
    private final WebClient webClient;
    private final ProductClientMapper productClientMapper;

    public ProductDetailApiClient(WebClient webClient, ProductClientMapper productClientMapper) {
        this.webClient = webClient;
        this.productClientMapper = productClientMapper;
    }

    @Override
    public Mono<Product> findById(ProductId id) {
        return webClient.get()
                .uri("/product/{id}", id.value())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ProductNotFoundException());
                    }
                    return Mono.error(new ProductGenericException("4xx from product API"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ProductGenericException("5xx from product API")))
                .bodyToMono(ProductClientResponseDTO.class)
                .timeout(Duration.ofSeconds(2))
                .map(productClientMapper::toDomain);
    }
}
