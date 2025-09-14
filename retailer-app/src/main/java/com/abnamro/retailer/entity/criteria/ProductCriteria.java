package com.abnamro.retailer.entity.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCriteria {

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BigDecimalFilter price;
}
