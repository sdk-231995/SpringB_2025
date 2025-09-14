package com.xyz.retail.dtos;

import lombok.Builder;

import javax.validation.constraints.NotNull;

@Builder
public record AddProductToCartRequest(@NotNull long customerId,
                                      @NotNull long productId,
                                      @NotNull int quantity) {
}
