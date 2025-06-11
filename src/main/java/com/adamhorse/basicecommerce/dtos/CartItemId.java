package com.adamhorse.basicecommerce.dtos;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class CartItemId implements Serializable {
    private UUID cartId;
    private Long productId;
}
