package com.adamhorse.basicecommerce.controllers;

import com.adamhorse.basicecommerce.dtos.*;
import com.adamhorse.basicecommerce.exceptions.CartNotFoundException;
import com.adamhorse.basicecommerce.exceptions.ProductNotFoundException;
import com.adamhorse.basicecommerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        CartDto cartDto = cartService.createCart();
        URI uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getUuid()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart.")
    public ResponseEntity<?> addItem(@Parameter(description = "The ID of the cart") @PathVariable UUID cartId,
                                     @Valid @RequestBody AddCartItemRequest request) {
        CartItemDto cartItemDto = cartService.addToCart(cartId, request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable UUID cartId) {
        CartDto cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(@PathVariable UUID cartId,
                                                      @PathVariable Long productId,
                                                      @Valid @RequestBody UpdateCartItemRequest request) {
        CartItemDto cartItemDto = cartService.updateCartItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId) {
        cartService.deleteCartItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart was not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in cart"));
    }

}
