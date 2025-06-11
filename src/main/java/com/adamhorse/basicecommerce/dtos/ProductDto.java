package com.adamhorse.basicecommerce.dtos;

import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
public class ProductDto {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Byte categoryId;

    public void setId(Long id) {

    }
}
