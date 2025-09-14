package com.xyz.retail.dtos;

import lombok.Builder;

@Builder
public record SaleAmountReportResponse(String date,
                                       String totalAmount) {
}
