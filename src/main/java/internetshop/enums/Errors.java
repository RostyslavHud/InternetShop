package internetshop.enums;

public enum Errors {
    INCORRECT_ID(1101, "Id must be below 0"),
    ORDER_NOT_FOUND(2404, "Order not found"),
    USER_NOT_FOUND(3404, "User not found"),
    PRODUCT_NOT_FOUND(4404, "Order not found"),
    EMPTY_ORDER(2405, "Order can't be empty"),
    EMPTY_ORDER_NUMBER(2406, "Order number can't be empty"),
    EMPTY_USER(3405, "User can't be empty"),
    EMPTY_USER_NAME(3406, "User name can't be empty"),
    SAME_USER_NAME(3407, "Username is already exist");

    private final int code;
    private final String message;

    Errors(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
