package com.adamhorse.basicecommerce.mappers;

import com.adamhorse.basicecommerce.dtos.CartDto;
import com.adamhorse.basicecommerce.entities.Cart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    @Mapping(target = "uuid", source = "id")
    CartDto toDto(Cart cart);
}
