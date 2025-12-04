package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.vo.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPriceClientMapper {

    @Mapping(target = "value", expression = "java(stringToDouble(price))")
    ProductPrice toProductPrice(String price);

    default double stringToDouble(String value) {
        return Double.parseDouble(value);
    }
}
