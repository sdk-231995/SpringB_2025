package com.abnamro.retailer.util;

public class ErrorConstants {

    public static final String ERROR_NAME_NOT_NULL = "Name field must NOT be Null";
    public static final String ERROR_NAME_NOT_EMPTY = "Name field must NOT be Empty";

    public static final String ERROR_PRICE_NOT_NULL = "Price field must NOT be Empty";
    public static final String ERROR_PRICE_POSITIVE = "Price field must be a number greater that or equals 1";

    public static final String ERROR_AVAILABLE_QUANTITY_NOT_NULL = "Available Quantity field must NOT be Empty";
    public static final String ERROR_AVAILABLE_QUANTITY_POSITIVE = "Available Quantity field must be a number greater that or equals 1";

    public static final String ERROR_CUSTOMER_NAME_NOT_NULL = "Customer Name field must NOT be Null";
    public static final String ERROR_CUSTOMER_NAME_NOT_EMPTY = "Customer Name field must NOT be Empty";

    public static final String ERROR_CUSTOMER_MOBILE_NOT_NULL = "Customer Mobile Number field must NOT be Null";
    public static final String ERROR_CUSTOMER_MOBILE_NOT_EMPTY = "Customer Mobile Number must NOT be Empty";

    public static final String ERROR_CUSTOMER_MOBILE_PATTERN_NOT_CORRECT = "Customer Mobile Number Pattern is NOT correct";
    public static final String ERROR_CUSTOMER_EMAIL_PATTERN_NOT_CORRECT = "Customer Email Pattern is NOT correct";

    public static final String ERROR_PRODUCT_ID_NOT_NULL = "Product Id field must NOT be Null";
    public static final String ERROR_PRODUCT_ID_POSITIVE = "Product Id field must be a positive Number";

    public static final String ERROR_QUANTITY_NOT_NULL = "Product Quantity field must NOT be Null";
    public static final String ERROR_QUANTITY_POSITIVE = "Quantity field must be a positive Number";

    public static final String ERROR_NAME_ALREADY_USED = "Name field already used";
    public static final String ERROR_PRODUCT_IDS_NOT_CORRECT = "Product IDs are not correct";
    public static final String ERROR_PRODUCT_SIZE_NOT_EMPTY = "Products must NOT be empty";
    public static final String ERROR_PRODUCT_QUANTITY_NOT_AVAILABLE = "Product(s) quantity not available for : ";
    public static final String ERROR_DATE_FROM_AFTER_TO = "FromDate must be before ToDate";
    public static final String GENERAL_ERROR = "GENERAL ERROR";

}
