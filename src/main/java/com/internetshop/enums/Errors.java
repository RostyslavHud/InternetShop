package com.internetshop.enums;

public enum Errors {
    INCORRECT_ID(1101, "error.incorrect-id", "id"),

    ORDER_NOT_FOUND(2404, "error.order-not-found", "orderClass"),
    EMPTY_ORDER(2405, "error.empty-order", "orderClass"),
    EMPTY_ORDER_NUMBER(2406, "error.empty-order-number", "orderNumber"),

    EMPTY_USER(3405, "error.empty-user", "userClass"),
    EMPTY_USER_NAME(3406, "error.empty-user-name", "userClass"),
    SAME_USER_NAME(3407, "error.same-user-name", "name"),
    SAME_USER_EMAIL(3408, "error.same-user-email", "email"),
    INCORRECT_USER_EMAIL(3408, "error.incorrect-user-email", "email"),
    USER_NOT_FOUND(3404, "error.user-not-found", "userClass"),

    PRODUCT_NOT_FOUND(4404, "error.product-not-found", "productClass"),
    EMPTY_PRODUCT(4405, "error.empty-product", "productClass"),

    EMPTY_TOKEN(5405, "error.empty-token", "token"),
    INCORRECT_TOKEN(5406, "error.incorrect-token", "token"),
    EXPIRY_DATE_TOKEN(5407, "error.token-time-is-over", "token"),

    CATEGORY_NOT_FOUND(6404, "error.category-not-found", "categoryClass"),

    LANGUAGE_NOT_FOUND(7404, "error.language-not-found", "languageClass"),

    USER_ATTEMPTS_NOT_FOUND(8404, "error.user-attempts-not-found", "userAttemptsClass");


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
