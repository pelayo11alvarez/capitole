package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.vo.ProductId;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import com.inditex.challenge.infrastructure.client.mapper.ProductClientMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class SimilarProductsApiClient implements SimilarProductsRepository {
    private static final String BASE_URL = "http://localhost:3001";
    private final WebClient webClient;
    private final ProductClientMapper productClientMapper;

    public SimilarProductsApiClient(WebClient webClient, ProductClientMapper productClientMapper) {
        this.webClient = webClient.mutate()
                .baseUrl(BASE_URL)
                .build();
        this.productClientMapper = productClientMapper;
    }

    @Override
    public Set<ProductId> findSimilarIds(ProductId id) {
        try {
            final var ids = webClient.get()
                    .uri("/product/{id}/similarids", id.value())
                    .retrieve()
                    .bodyToMono(String[].class)
                    .block();

            return productClientMapper.toProductIds(ids);
        } catch (WebClientResponseException.NotFound ex) {
            throw new ProductNotFoundException();
        }
    }
}
