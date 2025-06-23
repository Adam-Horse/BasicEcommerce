package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.payments.CheckoutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jpa.repository.EntityGraph;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", source = "id")
    CheckoutDto toCheckoutDto(Order order);


    @Mapping(target = "items", source = "orderItems")
    OrderDto toDto(Order order);

    @EntityGraph
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
