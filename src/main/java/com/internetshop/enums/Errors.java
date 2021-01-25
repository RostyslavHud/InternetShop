package com.internetshop.enums;

public enum Errors {
    INCORRECT_ID(1101, "Id must be below 0", "id"),

    ORDER_NOT_FOUND(2404, "Order not found", "orderClass"),
    EMPTY_ORDER(2405, "Order can't be empty", "orderClass"),
    EMPTY_ORDER_NUMBER(2406, "Order number can't be empty", "orderNumber"),

    EMPTY_USER(3405, "User can't be empty", "userClass"),
    EMPTY_USER_NAME(3406, "User name can't be empty", "userClass"),
    SAME_USER_NAME(3407, "Username is already exist", "name"),
    SAME_USER_EMAIL(3408, "There is user with such email is already exist", "email"),
    USER_NOT_FOUND(3404, "User not found", "userClass"),

    PRODUCT_NOT_FOUND(4404, "Order not found", "productClass"),

    EMPTY_TOKEN(5405, "Token can't be empty", "token"),
    INCORRECT_TOKEN(5406, "You fill incorrect token", "token"),
    EXPIRY_DATE_TOKEN(5407, "Time for user activation is over", "token");


    private final int code;
    private final String message;
    private final String field;

    Errors(int code, String message, String field) {
        this.code = code;
        this.message = message;
        this.field = field;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
