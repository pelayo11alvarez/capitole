package com.inditex.challenge.infrastructure.rest.mapper;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductDetailRequestMapperTest {

    @InjectMocks
    private ProductDetailRequestMapperImpl mapper;

    @Test
    void givenSimilarProducts_whenMappToProductDetailSet_thenReturnSetProductDetail() {
        final var similarProducts = Instancio.create(SimilarProducts.class);
        final var result = mapper.toProductDetailSet(similarProducts);
        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(similarProducts.products().size(), result.size())
        );
    }

    @Test
    void givenSetProduct_whenMappToProductDetailSet_thenReturnSetProductDetail() {
        final var products = Instancio.ofSet(Product.class)
                .size(1)
                .create();
        final var result = mapper.toProductDetailSet(products);
        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(products.size(), result.size())
        );
    }

    @Test
    void givenProduct_whenMappToProductDetail_thenReturnProductDetail() {
        final var product = Instancio.create(Product.class);
        final var result = mapper.toProductDetail(product);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(product.getId().value(), Long.parseLong(result.getId())),
                () -> assertEquals(product.getName().value(), result.getName()),
                () -> assertEquals(product.getPrice().value(), Double.parseDouble(result.getPrice().toString())),
                () -> assertEquals(product.isAvailable(), result.getAvailability())
        );
    }
}