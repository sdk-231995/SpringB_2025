package com.abnamro.retailer.service;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.repository.OrderRepository;
import static com.abnamro.retailer.util.TestUtils.buildRandomOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void findAll_getAllOrders() {
        List<Order> orders = List.of(buildRandomOrder(), buildRandomOrder(), buildRandomOrder());
        when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orderService.findAll().size(), orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findById_returnOrder() {
        Order order = buildRandomOrder();
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertEquals(orderService.findById(order.getId()), order);
    }

    @Test
    void findById_notFound_returnNull() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(orderService.findById(1L));
    }
}