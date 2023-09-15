package com.Dormitory.exception.roomreservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class NotSuitableForGender extends RuntimeException {

    private String message;

    private HttpStatusCode httpStatus = HttpStatus.BAD_REQUEST;

    public NotSuitableForGender(String message) {
        this.message = message;
    }

}
