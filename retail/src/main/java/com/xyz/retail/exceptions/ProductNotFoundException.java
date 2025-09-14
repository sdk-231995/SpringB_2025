package com.xyz.retail.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final String name) {
        super("Product with name: " + name + " not found");
    }
}
