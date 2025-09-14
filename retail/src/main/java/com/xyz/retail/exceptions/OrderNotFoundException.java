package com.xyz.retail.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(final String name) {
        super("Order with id: " + name + " not found");
    }
}
