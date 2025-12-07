package com.inditex.challenge.infrastructure.client.it;

import com.inditex.challenge.domain.exception.ProductGenericException;
import com.inditex.challenge.domain.exception.ProductNotFoundException;
import com.inditex.challenge.domain.model.identity.ProductId;
import com.inditex.challenge.infrastructure.client.SimilarProductsApiClient;
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

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimilarProductsApiClientITTest {

    private static final String PATH = "/product/{id}/similarids";
    private static final String HEADERS = "Content-Type";
    private static final String CONTENT_TYPE = "application/json";
    private static final int OK_CODE = 200;
    private static final int BAD_REQUEST_CODE = 400;
    private static final int NOT_FOUND_CODE = 404;
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;
    private static final int TIME_OUT_DELAY = 2;
    @Autowired
    private SimilarProductsApiClient similarProductsApiClient;
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
        final var similarProductUrl = baseUrl.substring(0, baseUrl.length() - PATH.length()) + PATH;

        registry.add("spring.infrastructure.similar-product.url", () -> similarProductUrl);
        registry.add("spring.infrastructure.product-detail.timeout", () -> 1);
    }

    @Test
    void givenProducId_whenFindSimilarIds_thenReturnIds() {
        //given
        final var productId = 1L;
        final var idsJson = "[2, 3, 4]";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK_CODE)
                .addHeader(HEADERS, CONTENT_TYPE)
                .setBody(idsJson));
        //when / then
        assertNotNull(similarProductsApiClient.findSimilarIds(new ProductId(productId)));
    }

    @Test
    void givenProducId_whenFindSimilarIds_thenThrowProductNotFoundException() {
        //given
        final var productId = 2L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(NOT_FOUND_CODE));
        //when / then
        assertThrows(ProductNotFoundException.class, () ->
                similarProductsApiClient.findSimilarIds(new ProductId(productId)));
    }

    @Test
    void givenProducId_whenFindSimilarIds_thenThrowProductGenericException() {
        //given
        final var productId = 3L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(INTERNAL_SERVER_ERROR_CODE));
        //when /then
        assertThrows(ProductGenericException.class, () ->
                similarProductsApiClient.findSimilarIds(new ProductId(productId)));
    }

    @Test
    void givenProducId_whenFindSimilarIds_thenThrowProductGenericExceptionByInvalidRequest() {
        //given
        final var productId = 4L;
        mockWebServer.enqueue(new MockResponse().setResponseCode(BAD_REQUEST_CODE));
        //when /then
        assertThrows(ProductGenericException.class, () ->
                similarProductsApiClient.findSimilarIds(new ProductId(productId)));
    }

    @Test
    void givenProducId_whenFindSimilarIds_thenThrowProductGenericExceptionByTimeout() {
        //given
        final var productId = 5L;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(OK_CODE)
                .setBody("[6, 7]")
                .setHeadersDelay(TIME_OUT_DELAY, TimeUnit.SECONDS));
        //when / then
        assertThrows(ProductGenericException.class, () ->
                similarProductsApiClient.findSimilarIds(new ProductId(productId)));
    }
}
