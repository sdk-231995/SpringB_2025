package com.obify.hy.ims.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {
    private String id;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String street;
    private String locality;
    private String city;
    private String zipCode;
    private String province;
    private String country;
}
