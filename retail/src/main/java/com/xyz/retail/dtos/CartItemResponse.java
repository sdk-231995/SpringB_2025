package com.xyz.retail.dtos;

import lombok.Builder;

@Builder
public record CartItemResponse(String customerId, String productId, String quantity) {
}
