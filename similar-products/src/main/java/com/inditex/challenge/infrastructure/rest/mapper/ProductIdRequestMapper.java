package com.inditex.challenge.infrastructure.rest.mapper;

import com.inditex.challenge.domain.model.vo.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductIdRequestMapper {

    @Mapping(target = "value", source = "id")
    ProductId toProductId(String id);
}
