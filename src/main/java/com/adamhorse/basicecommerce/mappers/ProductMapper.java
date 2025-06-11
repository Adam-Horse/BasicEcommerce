package com.adamhorse.basicecommerce.mappers;

import com.adamhorse.basicecommerce.dtos.ProductCartDto;
import com.adamhorse.basicecommerce.dtos.ProductDto;
import com.adamhorse.basicecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    ProductCartDto toCartDto(Product product);

    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    void update(ProductDto productDto, @MappingTarget Product product);
}
