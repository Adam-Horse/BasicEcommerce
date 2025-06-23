package com.adamhorse.basicecommerce.carts;

import com.adamhorse.basicecommerce.products.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    //Anemic domain model is when classes/entities only have data
    //Rich domain model is when classes/entities have data and behavior
    //This is a rich domain model, and is more applied to object-oriented programming
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    // "insertable = false, updatable = false" means MySQL will take care of generating the date
    // However, we are asking Spring to generate the date
    @Column(name = "date_created")
    private LocalDate dateCreated;

    // When a parent is saved (i.e. cart), the children are not saved (i.e. cartItems) unless they are persisted with cascade MERGE
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<CartItem> cartItems = new HashSet<>();

    public BigDecimal getTotalPrice() {
        // Turn stream of cartItems into stream of BigDecimals and them aggregate them with reduce
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public CartItem getItem(Long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product, Integer quantity) {
        CartItem cartItem = getItem(product.getId());

        if (quantity == null) {
            quantity = 1;
        }

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCartItemId(new CartItemId(this.id, product.getId()));
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartItem;
    }

    public boolean removeItem(Long productId) {
        CartItem cartItem = getItem(productId);
        if (cartItem != null) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
            return true;
        }
        return false;
    }

    public void clear() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
