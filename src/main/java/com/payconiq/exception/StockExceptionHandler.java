package com.payconiq.exception;


import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PessimisticLockException;
import java.time.OffsetDateTime;

/**
 * Global Exception Hanlder class for Stock Application
 *
 */
@ControllerAdvice
public class StockExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method to hanlde Custom Stock Exception
     *
      * @param ex reference of Stock Exception
     * @return ResponseEntity Object with Error Details
     */
    @ExceptionHandler(StockException.class)
    public ResponseEntity<Error> handleStockException(StockException ex)
    {
        Error err=new Error();
        err.setErrorCode(ex.getErrorCode());
        err.setErrorMessage(ex.getErrorMessage());
        err.setErrorOccurredTime(OffsetDateTime.now());
        if(ex.getErrorCode().equals("STOCK_NOT_EXIST"))
        {
            return  new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(err,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String fieldName = "";
        String message = "";
        JsonMappingException e = (JsonMappingException) ex.getCause();
        for (JsonMappingException.Reference reference : e.getPath()) {
            fieldName = reference.getFieldName();
        }
            message = fieldName + " can not take any letter or special characters";
        Error err=new Error();
        err.setErrorMessage(message);
        err.setErrorCode("INVALID_VALUE");
        err.setErrorOccurredTime(OffsetDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
   @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       String errorMessage =
               ex.getValue()+" Contains Invalid character or symbol";
       Error err=new Error();
       err.setErrorMessage(errorMessage);
       err.setErrorCode("INVALID_VALUE");
       err.setErrorOccurredTime(OffsetDateTime.now());
       return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
   }
}
