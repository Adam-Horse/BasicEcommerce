package com.adamhorse.basicecommerce.payments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutDto {
    private Long orderId;
    private String checkoutUrl;
}
