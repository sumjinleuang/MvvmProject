package com.sumjin.peppymvvm.common.model;

public class ApiException extends RuntimeException{

    private int code;

    public ApiException(int code,String message) {
        super(message);
        this.code=code;
    }
}
