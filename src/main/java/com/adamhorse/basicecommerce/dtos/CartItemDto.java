package com.adamhorse.basicecommerce.dtos;

import com.adamhorse.basicecommerce.entities.Product;
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
