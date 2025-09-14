package com.xyz.retail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(OrderNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String orderNotFoundHandler(final OrderNotFoundException e) {
//        return e.getMessage();
//    }

    @ExceptionHandler({ProductNotFoundException.class, OrderNotFoundException.class, CartItemsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(final Exception e) {
        return e.getMessage();
    }
}
