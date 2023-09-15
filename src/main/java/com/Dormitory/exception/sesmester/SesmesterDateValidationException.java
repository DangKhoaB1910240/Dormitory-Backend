package com.Dormitory.exception.sesmester;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class SesmesterDateValidationException extends RuntimeException {

    private String message;

    private HttpStatusCode httpStatus = HttpStatus.BAD_REQUEST;

    public SesmesterDateValidationException(String message) {
        this.message = message;
    }

}
