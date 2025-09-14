package com.xyz.retail.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(String customerName,
                            String customerPhoneNumber,
                            double totalAmount,
                            List<OrderItemResponse> orderItems) {
}
