package com.adamhorse.basicecommerce.carts;

import com.adamhorse.basicecommerce.products.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId cartItemId;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart", insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    private int quantity;

    public BigDecimal getTotalPrice() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
