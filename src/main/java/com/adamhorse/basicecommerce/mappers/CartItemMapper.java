package com.adamhorse.basicecommerce.mappers;

import com.adamhorse.basicecommerce.dtos.CartItemDto;
import com.adamhorse.basicecommerce.dtos.CartItemId;
import com.adamhorse.basicecommerce.entities.Cart;
import com.adamhorse.basicecommerce.entities.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toCartItemDto(CartItem cartItem);
}
