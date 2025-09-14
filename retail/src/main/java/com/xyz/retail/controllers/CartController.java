package com.xyz.retail.controllers;

import com.xyz.retail.dtos.AddProductToCartRequest;
import com.xyz.retail.dtos.CartItemResponse;
import com.xyz.retail.dtos.DtoConverter;
import com.xyz.retail.entities.CartItemEntity;
import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.services.CartItemService;
import com.xyz.retail.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final DtoConverter dtoConverter;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addProductToCart(@RequestBody final AddProductToCartRequest request) {
        final ProductEntity productEntity = productService.getProductById(request.productId());
        final CartItemEntity cartItemEntity = cartItemService.addProductToCart(request.customerId(), productEntity, request.quantity());
        final CartItemResponse cartItemResponse = dtoConverter.convertCartItemEntityToCartItemResponse(cartItemEntity);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
    }
}
