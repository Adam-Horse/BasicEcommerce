package com.adamhorse.basicecommerce.orders;

import com.adamhorse.basicecommerce.payments.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private PaymentStatus status;
    private LocalDate createdAt;
    private Set<OrderItemDto> items;
    private BigDecimal totalPrice;
}
