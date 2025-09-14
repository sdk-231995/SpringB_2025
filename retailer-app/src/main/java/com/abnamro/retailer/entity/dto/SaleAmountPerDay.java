package com.abnamro.retailer.entity.dto;

import static com.abnamro.retailer.util.ApplicationUtils.buildAndFormatDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleAmountPerDay {

    private String date;

    private BigDecimal totalAmount;

    public SaleAmountPerDay(Integer year, Integer month, Integer day, BigDecimal totalAmount) {
        this.date = buildAndFormatDate(year, month, day);
        this.totalAmount = totalAmount;
    }
}
