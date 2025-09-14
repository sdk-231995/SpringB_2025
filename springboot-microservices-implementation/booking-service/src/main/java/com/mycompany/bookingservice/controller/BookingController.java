package com.mycompany.bookingservice.controller;

import com.mycompany.bookingservice.client.StockClient;
import com.mycompany.bookingservice.dto.OrderDTO;
import com.mycompany.bookingservice.entity.Order;
import com.mycompany.bookingservice.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockClient stockClient;


    @PostMapping("/order")
    @HystrixCommand(fallbackMethod = "fallbackForStockAPI")
    public String submitOrder(@RequestBody OrderDTO orderDTO){

        boolean inStock = orderDTO.getOrderItems().stream()
                .allMatch(orderItem -> stockClient.stockCheck(orderItem.getCode()));

        if(inStock) {
            Order order = new Order();
            order.setOrderNo(UUID.randomUUID().toString());
            order.setOrderItems(orderDTO.getOrderItems());

            orderRepository.save(order);
            return "Order Placed!";
        }else{
            return "Order Cannot be Placed!";
        }
    }

    private String fallbackForStockAPI(){
        return "Something went wrong while fetching the product stock, please try after sometime";
    }

}
