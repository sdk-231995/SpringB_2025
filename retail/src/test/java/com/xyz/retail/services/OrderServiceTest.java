package com.xyz.retail.services;

import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.entities.OrderItemEntity;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private CartItemService cartItemService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Captor
    ArgumentCaptor<List<OrderItemEntity>> orderItemEntitiesArgumentCaptor;

    @Captor
    ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor;

    @Test
    void shouldPlaceOrder() {
        // given
        final long customerId = 1L;
        final double price = 1000.0;
        final ProductEntity givenProduct = ProductEntity.builder().id(123L).name("laptop").price(price).quantityAvailable(12).build();
        final double totalAmount = 2000.0;
        final String customerName = "name";
        final String customerPhoneNumber = "123213132";
        final OrderEntity givenOrder = OrderEntity.builder().id(1L).createdDate(new Date())
                .customerName(customerName).customerPhoneNumber(customerPhoneNumber).totalAmount(totalAmount).orderItemEntities(List.of(OrderItemEntity.builder().build())).build();
        final int quantity = 2;
        given(cartItemService.getCustomerById(customerId)).willReturn(List.of(CartItemEntity.builder()
                .customerId(customerId).quantity(quantity).productEntity(givenProduct).build()));
        given(orderRepository.save(any())).willReturn(givenOrder);
        given(productService.getProductById(any())).willReturn(givenProduct);

        // when
        final OrderEntity order = orderService.placeOrder(customerId, customerName, customerPhoneNumber);

        // then
        verify(cartItemService).getCustomerById(customerId);
        verify(orderRepository).save(orderEntityArgumentCaptor.capture());
        verify(orderItemService).saveOrderItems(orderItemEntitiesArgumentCaptor.capture());
        verify(cartItemService).deleteCustomer(customerId);
        final OrderEntity actualOrder = orderEntityArgumentCaptor.getValue();
        assertEquals(actualOrder.getTotalAmount(), order.getTotalAmount());
        assertEquals(actualOrder.getCustomerName(), order.getCustomerName());
        assertEquals(actualOrder.getCustomerPhoneNumber(), order.getCustomerPhoneNumber());
        final List<OrderItemEntity> actualOrderItemEntities = orderItemEntitiesArgumentCaptor.getValue();
        assertEquals(actualOrderItemEntities.get(0).getQuantity(), quantity);
        assertEquals(actualOrderItemEntities.get(0).getTotalPrice(), totalAmount);
    }
}