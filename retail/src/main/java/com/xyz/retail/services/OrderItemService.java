package com.xyz.retail.services;

import com.xyz.retail.entities.OrderItemEntity;
import com.xyz.retail.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<Object[]> retrieveTopFiveProductsOfTheDay() {
        return orderItemRepository.retrieveTop5SellingProductsOfDay();
    }

    public void saveOrderItems(List<OrderItemEntity> orderItemEntities) {
        orderItemRepository.saveAll(orderItemEntities);
    }

    public List<Object[]> retrieveLeastSellingProductsOfTheMonth() {
        return orderItemRepository.retrieveLeastSellingProductsOfTheMonth();
    }

}
