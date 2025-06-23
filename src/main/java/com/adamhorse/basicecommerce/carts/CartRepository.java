package com.adamhorse.basicecommerce.carts;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    // These prevent N + 1 queries, as now the cart and its items will all be loaded at once, instead of loaded at different times
    // EntityGraph eagerly loads the items with a left join. cartItems is the Set<CartItem> in Cart Entity
    @EntityGraph(attributePaths = "cartItems.product")
    // Query just finds cart
    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID cartId);
}
