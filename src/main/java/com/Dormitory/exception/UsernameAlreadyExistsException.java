package com.Dormitory.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException {

    private String message;

    private HttpStatus httpStatus;

    public UsernameAlreadyExistsException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
