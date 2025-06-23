package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.authentication.AuthenticationService;
import com.adamhorse.basicecommerce.user.User;
import com.adamhorse.basicecommerce.user.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final AuthenticationService authService;
    private final OrderRepository orderRepository;

    public List<OrderDto> getOrders() {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }

        List<Order> orders = orderRepository.findOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!order.isPlacedBy(user)) {
            throw new WrongOrderException();
        }

        return orderMapper.toDto(order);
    }
}
