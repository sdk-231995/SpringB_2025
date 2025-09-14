package com.xyz.retail.controllers;

import com.xyz.retail.dtos.DtoConverter;
import com.xyz.retail.dtos.ProductSearchResponse;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final DtoConverter dtoConverter;

    @GetMapping("/search")
    public List<ProductSearchResponse> searchProducts(@RequestParam("name") String name) {
        final List<ProductEntity> productEntities = productService.searchProducts(name);
        return productEntities.stream().map(dtoConverter::convertProductEntityToSearchResponse).toList();
    }
}
