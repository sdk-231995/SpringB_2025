package com.abnamro.retailer.entity.dto;

import com.abnamro.retailer.util.ErrorConstants;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDTO {

    @NotNull(message = ErrorConstants.ERROR_NAME_NOT_NULL)
    @NotEmpty(message = ErrorConstants.ERROR_NAME_NOT_EMPTY)
    private String name;

    private String description;

    @NotNull(message = ErrorConstants.ERROR_PRICE_NOT_NULL)
    @Min(value = 1, message = ErrorConstants.ERROR_PRICE_POSITIVE)
    private BigDecimal price;

    @NotNull(message = ErrorConstants.ERROR_AVAILABLE_QUANTITY_NOT_NULL)
    @Min(value = 1, message = ErrorConstants.ERROR_AVAILABLE_QUANTITY_POSITIVE)
    private Integer availableQuantity;
}
