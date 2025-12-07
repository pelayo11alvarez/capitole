package com.inditex.challenge.infrastructure.client.it;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.infrastructure.client.ProductDetailApiClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.GENERIC_CLIENT_ERROR;
import static com.inditex.challenge.domain.exception.constants.ExceptionConstants.GENERIC_INTERNAL_ERROR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductDetailApiClientITTest {

    private static final String PATH = "/product/{id}";
    private static final String HEADERS = "Content-Type";
    private static final String CONTENT_TYPE = "application/json";
    private static final int OK_CODE = 200;
    private static final int BAD_REQUEST_CODE = 400;
    private static final int NOT_FOUND_CODE = 404;
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;
    private static final int TIME_OUT_DELAY = 2;
    @Autowired
    private ProductDetailApiClient productDetailApiClient;
    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        final var baseUrl = mockWebServer.url(PATH).toString();
        final var productUrl = baseUrl.substring(0, baseUrl.length() - PATH.length()) + PATH;

        registry.add("spring.infrastructure.product-detail.url", () -> productUrl);
        registry.add("spring.infrastructure.product-detail.timeout", () -> 1);
    }

    @Test
    void givenProductId_whenFindById_thenReturnProduct() {
        //given
        final var productId = 1L;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK_CODE)
                .addHeader(HEADERS, CONTENT_TYPE)
                .setBody(productProvider()));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(new ProductId(productId)))
                .expectNextMatches(product -> {
                    assertNotNull(product);
                    assertTrue(product.getId().value() == (productId));
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void givenProductId_whenFindById_thenThrowProductNotFoundException() {
        //given
        final var productId = 2L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND_CODE));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(new ProductId(productId)))
                .expectError(ProductNotFoundException.class)
                .verify();
    }

    @Test
    void givenProductId_whenFindById_thenThrowProductGenericException() {
        //given
        final var productId = 3L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(INTERNAL_SERVER_ERROR_CODE));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(new ProductId(productId)))
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductGenericException &&
                                ((ProductGenericException) throwable).getMessage().equals(GENERIC_INTERNAL_ERROR))
                .verify();
    }

    @Test
    void givenProductId_whenFindById_thenThrowProductGenericExceptionByInvalidRequest() {
        //given
        final var productId = 4L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(BAD_REQUEST_CODE));

        //when / then
        StepVerifier.create(productDetailApiClient.findById(new ProductId(productId)))
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductGenericException &&
                                ((ProductGenericException) throwable).getMessage().equals(GENERIC_CLIENT_ERROR))
                .verify();
    }

    @Test
    void givenProductId_whenFindById_thenThrowProductGenericExceptionByTimeOut() {
        //given
        final var productId = 5L;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK_CODE)
                .setBody(productProvider())
                .setHeadersDelay(TIME_OUT_DELAY, TimeUnit.SECONDS)); // Retraso de 2 segundos

        //when / then
        StepVerifier.create(productDetailApiClient.findById(new ProductId(productId)))
                .expectErrorMatches(throwable ->
                        throwable instanceof ProductGenericException)
                .verify();
    }

    static String productProvider() {
        return "{\"id\": 1, \"name\": \"Dress\", \"price\": 29.99, \"stock\": 10}";
    }
}