package com.abnamro.retailer.entity.criteria;

import lombok.Data;

@Data
public class StringFilter extends Filter<String> {
    private String contains;
}