package com.payconiq.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Error {
    private  String errorMessage;
    private  String errorCode;
    private OffsetDateTime errorOccurredTime;
}
