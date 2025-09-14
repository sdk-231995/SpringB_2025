package com.xyz.retail.controllers;

import com.xyz.retail.dtos.PlaceOrderRequest;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.services.OrderService;
import com.xyz.retail.services.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final SmsService smsService;

    @PostMapping("/place")
    public ResponseEntity<Long> placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        final OrderEntity orderEntity = orderService.placeOrder(request.customerId(),
                request.customerName(), request.customerPhone());
        smsService.sendSMS(orderEntity, request.customerName(), request.customerPhone());
        return new ResponseEntity<>(orderEntity.getId(), HttpStatus.CREATED);
    }
}
