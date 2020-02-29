package com.rohov.internal.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String errMessage) {
        super(errMessage);
    }

}
