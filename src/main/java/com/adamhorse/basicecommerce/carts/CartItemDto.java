package com.adamhorse.basicecommerce.carts;

import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
public class CartItemDto {
    private final ProductCartDto product;
    private final int quantity;
    private BigDecimal totalPrice;
}
