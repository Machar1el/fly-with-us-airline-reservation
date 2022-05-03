package com.flywithus.airlinereservations.exception.user;

import com.flywithus.airlinereservations.exception.user.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    /* All UserExceptions are treated the same way because every subexception matches a HTTP 400 */

    @ExceptionHandler(value = { UserException.class })
    public ResponseEntity<Object> handleUserException(UserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
