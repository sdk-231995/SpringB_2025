package com.xyz.retail.dtos;

import lombok.Builder;

@Builder
public record OrderItemResponse(long orderId, long productId, int quantity, Double totalPrice) {
}
