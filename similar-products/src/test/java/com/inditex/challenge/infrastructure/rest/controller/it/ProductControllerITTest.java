package com.inditex.challenge.infrastructure.rest.controller.it;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerITTest {

    private static final int NOT_FOUND_CODE = 404;
    private static final int INTERNAL_SERVER_ERROR_CODE = 500;
    private static final String CONTENT_TYPE = "application/json";
    private static final String HEADERS = "Content-Type";
    private static final String NOT_FOUND_DESC = "Not Found";
    private static final String INTERNAL_SERVER_ERROR_DESC = "Internal Server Error";
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private MockWebServer mockWebServer;

    @Test
    void givenProductId_whenGetProductSimilar_thenReturnSimilarProducts() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(similarProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //first-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(firstProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //second-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(secondProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                        [
                            {"id":"2","name":"Dress","price":19.99,"availability":true},
                            {"id":"3","name":"Blazer","price":29.99,"availability":false}
                        ]
                        """);
    }

    @Test
    void givenProductId_whenGetProductSimilar_thenReturnProductDetailTimeOutException() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(similarProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //first-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(firstProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "2")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenNotExistsProductId_whenGetProductSimilar_thenReturnSimilarProductsNotFoud() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(NOT_FOUND_CODE)
                        .setBody(NOT_FOUND_DESC));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "11")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void givenProductId_whenGetProductSimilar_thenReturnGenericException() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(INTERNAL_SERVER_ERROR_CODE)
                        .setBody(INTERNAL_SERVER_ERROR_DESC));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "11")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void givenProductId_whenGetProductSimilar_thenReturnProductDetailNotFound() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(similarProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //first-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(firstProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //second-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(NOT_FOUND_CODE)
                        .setBody(NOT_FOUND_DESC));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "2")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void givenProductId_whenGetProductSimilar_thenReturnProductDetailGenericException() {
        //given
            //similar-products
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(similarProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //first-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setBody(firstProductProvider())
                        .addHeader(HEADERS, CONTENT_TYPE));
            //second-product-detail
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(INTERNAL_SERVER_ERROR_CODE)
                        .setBody(INTERNAL_SERVER_ERROR_DESC));

        //when /then
        webTestClient.get().uri("/product/{productId}/similar", "2")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    static String similarProductProvider() {
        return "[2,3]";
    }

    static String firstProductProvider() {
        return "{\"id\":\"2\",\"name\":\"Dress\",\"price\":19.99,\"availability\":true}";
    }

    static String secondProductProvider() {
        return "{\"id\":\"3\",\"name\":\"Blazer\",\"price\":29.99,\"availability\":false}";
    }

    @TestConfiguration
    static class TestConfig {
        @Bean(destroyMethod = "shutdown")
        @Primary
        MockWebServer mockWebServer() throws IOException {
            MockWebServer server = new MockWebServer();
            server.start();
            return server;
        }

        @Bean
        @Primary
        WebClient webClient(MockWebServer mockWebServer) {
            return WebClient.builder()
                    .baseUrl(mockWebServer.url("/").toString())
                    .build();
        }
    }
}
