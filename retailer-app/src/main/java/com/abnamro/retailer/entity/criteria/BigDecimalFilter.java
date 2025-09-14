package com.abnamro.retailer.entity.criteria;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BigDecimalFilter extends Filter<BigDecimal> {

    private BigDecimal greaterThanOrEquals;

    private BigDecimal lessThanOrEquals;

}