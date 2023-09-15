package com.Dormitory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.exception.room.RoomNotEnabledException;
import com.Dormitory.exception.roomreservation.NotSuitableForGender;
import com.Dormitory.exception.sesmester.SesmesterDateValidationException;
import com.Dormitory.message.ErrorMessage;

@RestControllerAdvice
public class CustomHandleException {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleUsernameAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorMessage errorMessage = new ErrorMessage("Validation failed", details, HttpStatusCode.valueOf(400).value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(ex.getMessage(),HttpStatusCode.valueOf(401).value()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorMessage> handleInvalidValueException(InvalidValueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }
    
    @ExceptionHandler(RoomNotEnabledException.class)
    public ResponseEntity<ErrorMessage> handleRoomNotEnabledException(RoomNotEnabledException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

    @ExceptionHandler(SesmesterDateValidationException.class)
    public ResponseEntity<ErrorMessage> handleSesmesterDateValidationException(SesmesterDateValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

    @ExceptionHandler(NotSuitableForGender.class)
    public ResponseEntity<ErrorMessage> handleNotSuitableForGender(NotSuitableForGender ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), HttpStatusCode.valueOf(400).value()));
    }

}
