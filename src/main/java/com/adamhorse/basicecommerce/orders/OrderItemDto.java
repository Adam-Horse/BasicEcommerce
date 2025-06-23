package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.carts.ProductCartDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private ProductCartDto product;
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal totalPrice;
}
