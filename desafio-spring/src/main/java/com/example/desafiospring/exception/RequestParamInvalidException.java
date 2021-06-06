package com.example.desafiospring.exception;

public class RequestParamInvalidException extends Exception{
    public RequestParamInvalidException(String message) {
        super(message);
    }
}
