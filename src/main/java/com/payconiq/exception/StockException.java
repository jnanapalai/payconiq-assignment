package com.payconiq.exception;


import lombok.Getter;

@Getter
public class StockException extends  Exception {
    private final String errorMessage;
    private final String errorCode;


    public StockException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
