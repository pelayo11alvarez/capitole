package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.Product;
import com.inditex.challenge.domain.model.vo.ProductId;
import com.inditex.challenge.infrastructure.client.dto.ProductClientResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductClientMapper {

    @Mapping(target = "id", expression = "java(new ProductId(dto.id()))")
    @Mapping(target = "availability", source = "availability")
    Product toDomain(ProductClientResponseDTO dto);

    default List<ProductId> toProductIds(String[] ids) {
        return Arrays.stream(ids)
                .map(ProductId::new)
                .toList();
    }
}
