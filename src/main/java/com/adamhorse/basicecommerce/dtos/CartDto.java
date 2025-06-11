package com.adamhorse.basicecommerce.dtos;

import com.adamhorse.basicecommerce.entities.CartItem;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Setter
public class CartDto {
    private final UUID uuid;
    private final Set<CartItemDto> cartItems;
    private BigDecimal totalPrice;

}
