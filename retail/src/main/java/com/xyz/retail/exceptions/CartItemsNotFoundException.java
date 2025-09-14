package com.xyz.retail.exceptions;

public class CartItemsNotFoundException extends RuntimeException {

    public CartItemsNotFoundException(final Long id) {
        super("Customer with id: " + id + " has no associated cart items");
    }
}
