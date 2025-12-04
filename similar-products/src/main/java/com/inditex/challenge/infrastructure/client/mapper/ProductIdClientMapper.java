package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.identity.ProductId;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductIdClientMapper {

    default Set<ProductId> toProductIds(String[] ids) {
        return Arrays.stream(ids)
                .map(ProductId::new)
                .collect(Collectors.toSet());
    }
}
