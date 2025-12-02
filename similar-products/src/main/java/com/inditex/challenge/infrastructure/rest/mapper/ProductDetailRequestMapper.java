package com.inditex.challenge.infrastructure.rest.mapper;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.vo.SimilarProducts;
import com.inditex.challenge.infrastructure.rest.api.model.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductDetailRequestMapper {

    default Set<ProductDetail> toProductDetailSet(SimilarProducts similarProducts) {
        return toProductDetailSet(similarProducts.products());
    }

    Set<ProductDetail> toProductDetailSet(Set<Product> products);

    @Mapping(target = "id", source = "product.id.value")
    @Mapping(target = "availability", source = "available")
    ProductDetail toProductDetail(Product product);
}
