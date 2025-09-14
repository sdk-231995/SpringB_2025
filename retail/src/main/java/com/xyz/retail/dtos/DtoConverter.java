package com.xyz.retail.dtos;

import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.OrderEntity;
import com.xyz.retail.entities.OrderItemEntity;
import com.xyz.retail.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    public OrderResponse convertOrderEntityToOrderResponse(final OrderEntity orderEntity) {
        return OrderResponse.builder()
                .customerName(orderEntity.getCustomerName())
                .customerPhoneNumber(orderEntity.getCustomerPhoneNumber())
                .totalAmount(orderEntity.getTotalAmount())
                .orderItems(orderEntity.getOrderItemEntities().stream().map(this::convertOrderItemEntityToOrderItemResponse).toList())
                .build();
    }

    public OrderItemResponse convertOrderItemEntityToOrderItemResponse(final OrderItemEntity orderItemEntity) {
        return OrderItemResponse.builder()
                .orderId(orderItemEntity.getOrderEntity().getId())
                .productId(orderItemEntity.getProductEntity().getId())
                .totalPrice(orderItemEntity.getTotalPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    public CartItemResponse convertCartItemEntityToCartItemResponse(final CartItemEntity cartItemEntity) {
        return CartItemResponse.builder()
                .customerId(String.valueOf(cartItemEntity.getCustomerId()))
                .productId(String.valueOf(cartItemEntity.getProductEntity().getId()))
                .quantity(String.valueOf(cartItemEntity.getQuantity()))
                .build();
    }

    public ProductSearchResponse convertProductEntityToSearchResponse(final ProductEntity productEntity) {
        return ProductSearchResponse.builder()
                .id(String.valueOf(productEntity.getId()))
                .name(String.valueOf(productEntity.getName()))
                .price(String.valueOf(productEntity.getPrice()))
                .quantityAvailable(String.valueOf(productEntity.getQuantityAvailable()))
                .build();
    }
}
