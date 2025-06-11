package com.adamhorse.basicecommerce.services;

import com.adamhorse.basicecommerce.dtos.CartDto;
import com.adamhorse.basicecommerce.dtos.CartItemDto;
import com.adamhorse.basicecommerce.entities.Cart;
import com.adamhorse.basicecommerce.entities.CartItem;
import com.adamhorse.basicecommerce.entities.Product;
import com.adamhorse.basicecommerce.exceptions.CartNotFoundException;
import com.adamhorse.basicecommerce.exceptions.ProductNotFoundException;
import com.adamhorse.basicecommerce.mappers.CartItemMapper;
import com.adamhorse.basicecommerce.mappers.CartMapper;
import com.adamhorse.basicecommerce.repositories.CartRepository;
import com.adamhorse.basicecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private CartItemMapper cartItemMapper;
    private ProductRepository productRepository;

    public CartDto createCart() {
        Cart cart = new Cart();
        cart.setDateCreated(LocalDate.now());

        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        //TODO: increase/update quantity if item is already in cart!
        CartItem cartItem = cart.addItem(product, quantity);
        cartRepository.save(cart);

        return cartItemMapper.toCartItemDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        CartItem cartItem = cart.getItem(productId);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartItemMapper.toCartItemDto(cartItem);
    }

    public void deleteCartItem(UUID cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        // Information Expert Principle. The class that has the data to do the job
        // should be assigned the responsibility to do the job.
        // In this case, instead of
        /*
            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);
        */
        // Because the cart class has all the necessary information to implement this logic,
        // It should be handled there, not here
        boolean exists = cart.removeItem(productId);
        if (!exists) {
            throw new ProductNotFoundException();
        }

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.clear();
        cartRepository.save(cart);
    }
}
