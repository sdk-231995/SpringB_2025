package com.xyz.retail.dtos;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record PlaceOrderRequest(@NotNull(message = "The customerId is required.") long customerId,
                                @NotBlank(message = "The customerName is required.") String customerName,
                                @NotBlank(message = "The customerPhone is required.") String customerPhone) {
}
