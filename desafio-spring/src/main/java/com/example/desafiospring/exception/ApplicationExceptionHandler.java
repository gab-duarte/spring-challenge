package com.example.desafiospring.exception;

import com.example.desafiospring.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UserInvalidException.class, RequestParamInvalidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, Exception.class})
    public static ResponseEntity<ErrorDTO> handleException(Exception e){
        ErrorDTO errorDTO;

        if(e instanceof UserNotFoundException){
            errorDTO = new ErrorDTO("User Not Found", e.getMessage() + " not found");
            return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }
        else if(e instanceof UserInvalidException){
            errorDTO = new ErrorDTO("User Invalid", e.getMessage() + " is invalid");
        }
        else if(e instanceof RequestParamInvalidException){
            errorDTO = new ErrorDTO("Request Param Invalid",  e.getMessage() + " is invalid.");
        }
        else if(e instanceof PostInvalidException){
            errorDTO = new ErrorDTO("Post Invalid",  e.getMessage() + " is invalid.");
        }
        else if(e instanceof MethodArgumentTypeMismatchException){
            errorDTO = new ErrorDTO("Path Variable Invalid",  e.getMessage());
        }
        else if(e instanceof HttpMessageNotReadableException){
            errorDTO = new ErrorDTO("Request Body Invalid", e.getMessage());
        }
        else{
            errorDTO = new ErrorDTO("Bad Request", "Error processing your request");
        }

        return  new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
