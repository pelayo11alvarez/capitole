package com.inditex.challenge.infrastructure.client;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import com.inditex.challenge.domain.port.out.SimilarProductsRepository;
import com.inditex.challenge.infrastructure.client.mapper.SimilarProductsIdMapper;
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
public class SimilarProductsApiClient implements SimilarProductsRepository {
    private final WebClient webClient;
    private final SimilarProductsIdMapper similarProductsIdMapper;
    private final String similarProductSimuladoUrl;
    private final int timeout;

    public SimilarProductsApiClient(WebClient webClient, SimilarProductsIdMapper similarProductsIdMapper,
                                    @Value("${spring.infrastructure.similar-product.url}")
                                    String similarProductSimuladoUrl,
                                    @Value("${spring.infrastructure.product-detail.timeout}")
                                    int timeout) {
        this.webClient = webClient;
        this.similarProductsIdMapper = similarProductsIdMapper;
        this.similarProductSimuladoUrl = similarProductSimuladoUrl;
        this.timeout = timeout;
    }

    @Override
    public SimilarProductsId findSimilarIds(ProductId id) {
        return webClient.get()
                .uri(similarProductSimuladoUrl, id.value())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ProductNotFoundException());
                    }
                    return Mono.error(new ProductGenericException(GENERIC_CLIENT_ERROR));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ProductGenericException(GENERIC_INTERNAL_ERROR)))
                .bodyToMono(long[].class)
                .timeout(Duration.ofSeconds(timeout))
                .onErrorMap(TimeoutException.class,
                        ex -> new ProductGenericException(GENERIC_TIMEOUT_ERROR))
                .map(similarProductsIdMapper::toSimilarProductsId)
                .block();
    }
}
