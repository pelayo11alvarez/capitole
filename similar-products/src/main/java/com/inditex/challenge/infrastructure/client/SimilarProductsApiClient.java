package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import com.inditex.challenge.infrastructure.client.mapper.ProductIdClientMapper;
import com.inditex.challenge.infrastructure.client.mapper.SimilarProductsIdMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;

@Component
public class SimilarProductsApiClient implements SimilarProductsRepository {
    private final WebClient webClient;
    private final SimilarProductsIdMapper similarProductsIdMapper;

    public SimilarProductsApiClient(WebClient webClient, SimilarProductsIdMapper similarProductsIdMapper) {
        this.webClient = webClient;
        this.similarProductsIdMapper = similarProductsIdMapper;
    }

    @Override
    public SimilarProductsId findSimilarIds(ProductId id) {
        return webClient.get()
                .uri("/product/{id}/similarids", id.value())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ProductNotFoundException());
                    }
                    return Mono.error(new ProductGenericException("4xx from product API"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ProductGenericException("5xx from product API")))
                .bodyToMono(long[].class)
                .timeout(Duration.ofSeconds(2))
                .map(similarProductsIdMapper::toSimilarProductsId)
                .block();
    }
}
