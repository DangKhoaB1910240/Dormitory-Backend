package com.Dormitory.exception.room;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class RoomNotEnabledException extends RuntimeException {

    private String message;

    private HttpStatusCode httpStatus = HttpStatus.BAD_REQUEST;

    public RoomNotEnabledException(String message) {
        this.message = message;
    }

}
