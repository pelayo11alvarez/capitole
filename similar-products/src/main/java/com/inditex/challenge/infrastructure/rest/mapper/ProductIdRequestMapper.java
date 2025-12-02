package com.inditex.challenge.infrastructure.rest.mapper;

import com.inditex.challenge.domain.model.vo.ProductId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductIdRequestMapper {

    ProductId toProductId(String id);
}
