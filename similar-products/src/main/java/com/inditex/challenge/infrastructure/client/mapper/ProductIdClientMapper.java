package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.identity.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductIdClientMapper {

    @Mapping(target = "value", source = "id")
    ProductId toProductId(String id);
    Set<ProductId> toProductIds(long[] ids);
}
