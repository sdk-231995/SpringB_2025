package com.xyz.retail.dtos;

import lombok.Builder;

@Builder
public record ProductSearchResponse(String id,
                                    String name,
                                    String price,
                                    String quantityAvailable) {
}
