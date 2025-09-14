package com.xyz.retail.services;

import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemEntity addProductToCart(final Long customerId, final ProductEntity productEntity, final int quantity) {
        final CartItemEntity cartItemEntity = CartItemEntity.builder().customerId(customerId).productEntity(productEntity).quantity(quantity).build();
        return cartItemRepository.save(cartItemEntity);
    }

    public List<CartItemEntity> getCustomerById(final long customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    public void deleteCustomer(final long customerId) {
        cartItemRepository.deleteByCustomerId(customerId);
    }
}
