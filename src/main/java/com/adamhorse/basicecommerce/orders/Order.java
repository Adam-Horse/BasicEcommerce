package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.carts.Cart;
import com.adamhorse.basicecommerce.payments.PaymentStatus;
import com.adamhorse.basicecommerce.products.Product;
import com.adamhorse.basicecommerce.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public OrderItem getItem(Long productId) {
        return orderItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void addItem(Product product, Integer quantity) {
        OrderItem orderItem = getItem(product.getId());

        if (quantity == null) {
            quantity = 1;
        }

        if (orderItem == null) {
            orderItem = new OrderItem();
            orderItem.setOrder(this);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            orderItems.add(orderItem);
        } else {
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
        }

    }

    public static Order fromCart(Cart cart, User user) {
        Order order = new Order();
        cart.getCartItems().forEach(cartItem -> {
            order.addItem(cartItem.getProduct(), cartItem.getQuantity());
        });
        order.setCustomer(user);
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(PaymentStatus.PENDING);

        return order;
    }

    public boolean isPlacedBy(User customer) {
        return this.customer.equals(customer);
    }

}