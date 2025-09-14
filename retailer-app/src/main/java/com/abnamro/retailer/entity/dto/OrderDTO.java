package com.abnamro.retailer.entity.dto;

import com.abnamro.retailer.util.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {

    @NotNull(message = ErrorConstants.ERROR_CUSTOMER_NAME_NOT_NULL)
    @NotEmpty(message = ErrorConstants.ERROR_CUSTOMER_NAME_NOT_EMPTY)
    private String customerName;

    @NotNull(message = ErrorConstants.ERROR_CUSTOMER_MOBILE_NOT_NULL)
    @NotEmpty(message = ErrorConstants.ERROR_CUSTOMER_MOBILE_NOT_EMPTY)
    @Pattern(regexp = "^(\\+\\d{1,3})?\\d{10}$", message = ErrorConstants.ERROR_CUSTOMER_MOBILE_PATTERN_NOT_CORRECT)
    private String customerPhone;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = ErrorConstants.ERROR_CUSTOMER_EMAIL_PATTERN_NOT_CORRECT)
    private String customerEmail;

    @Valid
    @Size(min = 1, message = ErrorConstants.ERROR_PRODUCT_SIZE_NOT_EMPTY)
    private List<OrderProductDTO> products;

}
