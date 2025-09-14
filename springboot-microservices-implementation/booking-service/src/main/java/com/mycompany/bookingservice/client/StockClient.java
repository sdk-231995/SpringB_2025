package com.mycompany.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-service")//will make sure to use the eureka server name to make the API call from booking to stock service
public interface StockClient {

    @GetMapping("/api/stock/{code}")
    boolean stockCheck(@PathVariable String code);
}
