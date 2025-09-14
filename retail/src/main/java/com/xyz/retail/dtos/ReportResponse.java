package com.xyz.retail.dtos;

import lombok.Builder;

@Builder
public record ReportResponse(String productName,
                             String totalQuantitySold,
                             String quantityAvailable,
                             String price,
                             boolean isNearingOutOfStock) {
}
