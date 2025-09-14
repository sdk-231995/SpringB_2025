package com.xyz.retail.services;

import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.exceptions.ProductNotFoundException;
import com.xyz.retail.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductEntity> searchProducts(final String name) {
        final List<ProductEntity> productEntities = productRepository.findByName(name);
        if (productEntities.isEmpty()) {
            throw new ProductNotFoundException(name);
        }
        return productEntities;
    }

    public ProductEntity getProductById(final Long id) {
        return productRepository.findById(id).orElse(null);
    }
}

