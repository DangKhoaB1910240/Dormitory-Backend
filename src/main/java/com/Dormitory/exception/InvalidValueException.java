package com.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class InvalidValueException extends RuntimeException {

    private String message;

    private HttpStatusCode httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidValueException(String message) {
        this.message = message;
    }

}
