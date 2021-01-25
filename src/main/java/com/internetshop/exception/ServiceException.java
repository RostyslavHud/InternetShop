package com.internetshop.exception;

import com.internetshop.enums.Errors;
import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private final Errors errors;
    public ServiceException(Errors errors) {
        super(errors.getMessage());
        this.errors = errors;
    }
}
