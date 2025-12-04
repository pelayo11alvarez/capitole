package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.vo.ProductName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductNameClientMapper {

    @Mapping(target = "value", source = "name")
    ProductName toProductName(String name);
}
