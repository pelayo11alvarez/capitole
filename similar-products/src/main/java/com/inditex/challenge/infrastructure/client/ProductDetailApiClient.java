package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.port.out.ProductDetailRepository;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import com.inditex.challenge.infrastructure.client.mapper.ProductClientMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.*;

@Component
public class ProductDetailApiClient implements ProductDetailRepository {
    private final WebClient webClient;
    private final ProductClientMapper productClientMapper;
    private final String productDetailSimuladoUrl;
    private final int timeout;

    public ProductDetailApiClient(WebClient webClient, ProductClientMapper productClientMapper,
                                  @Value("${spring.infrastructure.product-detail.url}")
                                  String productDetailSimuladoUrl,
                                  @Value("${spring.infrastructure.product-detail.timeout}")
                                  int timeout) {
        this.webClient = webClient;
        this.productClientMapper = productClientMapper;
        this.productDetailSimuladoUrl = productDetailSimuladoUrl;
        this.timeout = timeout;
    }

    @Override
    public Mono<Product> findById(ProductId id) {
        return webClient.get()
                .uri(productDetailSimuladoUrl, id.value())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ProductNotFoundException());
                    }
                    return Mono.error(new ProductGenericException(GENERIC_CLIENT_ERROR));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ProductGenericException(GENERIC_INTERNAL_ERROR)))
                .bodyToMono(ProductClientResponseDTO.class)
                .timeout(Duration.ofSeconds(timeout))
                .onErrorMap(TimeoutException.class,
                        ex -> new ProductGenericException(GENERIC_TIMEOUT_ERROR))
                .map(productClientMapper::toDomain);
    }
}
