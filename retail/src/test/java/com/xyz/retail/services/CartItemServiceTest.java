package com.xyz.retail.services;

import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.repositories.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @Captor
    ArgumentCaptor<CartItemEntity> productEntityArgumentCaptor;

    @Test
    void shouldAddProductToCart() {
        // given
        final long customerId = 3211L;
        final int quantity = 3;
        final ProductEntity givenProduct = ProductEntity.builder().id(1L).name("product").price(100.0).quantityAvailable(10).build();

        // when
        cartItemService.addProductToCart(customerId, givenProduct, quantity);

        // then
        verify(cartItemRepository).save(productEntityArgumentCaptor.capture());
        final CartItemEntity captorValue = productEntityArgumentCaptor.getValue();
        assertEquals(captorValue.getCustomerId(), customerId);
        assertEquals(captorValue.getQuantity(), quantity);
        assertEquals(captorValue.getProductEntity().getName(), givenProduct.getName());
    }
}