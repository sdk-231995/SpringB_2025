package com.xyz.retail.services;

import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSearchProduct() {
        // given
        final String productName = "laptop";
        final ProductEntity givenProduct = ProductEntity.builder().id(123L).name(productName).price(1500.0).quantityAvailable(12).build();
        given(productRepository.findByName(productName)).willReturn(List.of(givenProduct));

        // when
        final List<ProductEntity> productEntities = productService.searchProducts(productName);

        // then
        verify(productRepository).findByName(productName);
        assertEquals(productEntities.get(0).getName(), givenProduct.getName());
    }

    @Test
    void shouldGetProductById() {
        // given
        final long productId = 143L;

        // when
        productService.getProductById(productId);

        // then
        verify(productRepository).findById(productId);
    }
}