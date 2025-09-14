package com.abnamro.retailer.service;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.criteria.ProductCriteria;
import com.abnamro.retailer.repository.ProductRepository;
import static com.abnamro.retailer.util.ErrorConstants.ERROR_NAME_ALREADY_USED;
import static com.abnamro.retailer.util.TestUtils.buildRandomProduct;
import static com.abnamro.retailer.util.TestUtils.buildRandomProductWithoutId;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void saveProduct_validData_savedInDB() {
        Product product = buildRandomProductWithoutId();

        Product productFromBD = buildRandomProduct();
        when(productRepository.save(eq(product))).thenReturn(productFromBD);

        Product result = productService.save(product);
        assertEquals(result.getName(), productFromBD.getName());
        assertEquals(result.getDescription(), productFromBD.getDescription());
        assertEquals(result.getPrice(), productFromBD.getPrice());
    }

    @Test
    public void saveProduct_duplicateName_throwInvalidInputException() {
        Product product = buildRandomProductWithoutId();

        Product productFromBD = buildRandomProduct();
        when(productRepository.findByName(eq(product.getName()))).thenReturn(productFromBD);

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> productService.save(product))
                .withMessage(ERROR_NAME_ALREADY_USED);

    }

    @Test
    public void findAll_noCriteria_getAll() {
        List<Product> productsFromBD = List.of(buildRandomProduct(), buildRandomProduct(), buildRandomProduct());

        when(productRepository.findAll(any(Specification.class))).thenReturn(productsFromBD);

        ProductCriteria criteria = ProductCriteria.builder().build();
        List<Product> result = productService.findAll(criteria);
        assertSame(result, productsFromBD);
    }

    @Test
    public void findById_validId_getProduct() {
        Product productFromBD = buildRandomProduct();
        when(productRepository.findById(eq(1L))).thenReturn(Optional.of(productFromBD));

        assertSame(productFromBD, productService.findById(1L));
    }

    @Test
    public void findById_invalidId_getNull() {
        when(productRepository.findById(eq(1L))).thenReturn(Optional.empty());

        assertNull(productService.findById(1L));
    }
}