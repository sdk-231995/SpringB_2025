package com.abnamro.retailer.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportProductDTO {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer availableQuantity;

    private Boolean isLowQuantity;
}
