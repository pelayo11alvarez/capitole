package com.inditex.challenge.infrastructure.client.mapper;

import com.inditex.challenge.domain.model.vo.SimilarProductsId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProductIdClientMapper.class })
public interface SimilarProductsIdMapper {

    @Mapping(target = "values", source = "ids")
    SimilarProductsId toSimilarProductsId(long[] ids);
}
