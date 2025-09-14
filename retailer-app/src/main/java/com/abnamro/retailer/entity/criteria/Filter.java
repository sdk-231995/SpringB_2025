package com.abnamro.retailer.entity.criteria;

import lombok.Data;

@Data
public class Filter<TYPE> {
    private TYPE equals;
    private TYPE notEquals;
}