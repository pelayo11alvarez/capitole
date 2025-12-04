package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = { ProductIdClientMapper.class, ProductNameClientMapper.class, ProductPriceClientMapper.class })
public interface ProductClientMapper {

    @Mapping(target = "availability", source = "availability")
    Product toDomain(ProductClientResponseDTO dto);
}
