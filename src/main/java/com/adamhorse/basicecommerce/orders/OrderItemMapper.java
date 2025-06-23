package com.adamhorse.basicecommerce.orders;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto orderItemDto(OrderItem orderItem);
}
