package com.xyz.retail.services;

import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.entities.OrderItemEntity;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.exceptions.CartItemsNotFoundException;
import com.xyz.retail.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    private final ProductService productService;

    public OrderEntity getOrderById(final Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public OrderEntity placeOrder(final long customerId, final String customerName, final String customerPhoneNumber) {
        final List<CartItemEntity> cartItemEntities = cartItemService.getCustomerById(customerId);
        if (cartItemEntities.isEmpty()) {
            throw new CartItemsNotFoundException(customerId);
        }
        final List<OrderItemEntity> orderItemEntities = populateOrderItems(cartItemEntities);
        final OrderEntity orderEntity = OrderEntity.builder().customerName(customerName).customerPhoneNumber(customerPhoneNumber).createdDate(new Date()).build();
        orderEntity.setTotalAmount(orderItemEntities.stream().filter(Objects::nonNull).mapToDouble(OrderItemEntity::getTotalPrice).sum());
        final OrderEntity newOrderEntity = orderRepository.save(orderEntity);
        orderItemEntities.forEach(orderItemEntity -> orderItemEntity.setOrderEntity(newOrderEntity));
        orderItemService.saveOrderItems(orderItemEntities);
        cartItemService.deleteCustomer(customerId);
        log.info("Order created with id {}", newOrderEntity.getId());
        return newOrderEntity;
    }

    private List<OrderItemEntity> populateOrderItems(List<CartItemEntity> cartItemEntities) {
        return cartItemEntities.stream().map(cartItemEntity -> {
            final ProductEntity productEntity = cartItemEntity.getProductEntity();
            final double totalPrice = (double) cartItemEntity.getQuantity() * productEntity.getPrice();
            productService.getProductById(productEntity.getId()).setQuantityAvailable(productEntity.getQuantityAvailable() - cartItemEntity.getQuantity());
            return OrderItemEntity.builder().productEntity(productEntity).quantity(cartItemEntity.getQuantity())
                    .totalPrice(new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    .createdDate(new Date())
                    .build();
        }).toList();
    }

    public List<Object[]> getSaleAmountForDates(final LocalDate startDate, final LocalDate endDate) {
        return orderRepository.retrieveSaleAmountForDates(startDate, endDate);
    }
}