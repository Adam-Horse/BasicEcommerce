package com.adamhorse.basicecommerce.carts;

import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
public class ProductCartDto {
    private final Long id;
    private final String name;
    private final BigDecimal price;
}
