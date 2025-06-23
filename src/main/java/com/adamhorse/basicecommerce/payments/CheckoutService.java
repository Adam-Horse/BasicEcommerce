package com.adamhorse.basicecommerce.payments;

import com.adamhorse.basicecommerce.carts.Cart;
import com.adamhorse.basicecommerce.orders.Order;
import com.adamhorse.basicecommerce.user.User;
import com.adamhorse.basicecommerce.carts.CartEmptyException;
import com.adamhorse.basicecommerce.carts.CartNotFoundException;
import com.adamhorse.basicecommerce.user.UserNotFoundException;
import com.adamhorse.basicecommerce.carts.CartRepository;
import com.adamhorse.basicecommerce.orders.OrderRepository;
import com.adamhorse.basicecommerce.authentication.AuthenticationService;
import com.adamhorse.basicecommerce.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final AuthenticationService authService;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutDto checkout(UUID cartId) {
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }

        Order order = Order.fromCart(cart, user);

        orderRepository.save(order);

        try {
            CheckoutSession session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());

            return new CheckoutDto(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
            .parseWebhookRequest(request)
            .ifPresent(paymentResult -> {
                Order order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                order.setStatus(paymentResult.getPaymentStatus());
                orderRepository.save(order);
            });
    }
}
