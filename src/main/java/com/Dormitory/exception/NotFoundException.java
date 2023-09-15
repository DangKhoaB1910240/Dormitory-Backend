package com.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String message;

    private HttpStatusCode httpStatus = HttpStatus.NOT_FOUND ;

    public NotFoundException(String message) {
        this.message = message;
    }

}
