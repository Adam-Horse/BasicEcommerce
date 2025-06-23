package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.dtos.ErrorDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Order not found"));
    }

    @ExceptionHandler(WrongOrderException.class)
    public ResponseEntity<ErrorDto> handleWrongOrder() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Order is not yours!"));
    }
}
