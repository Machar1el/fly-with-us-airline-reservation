package com.flywithus.airlinereservations.exception.user;

import com.flywithus.airlinereservations.dto.ErrorDTO;
import com.flywithus.airlinereservations.exception.user.exception.UserNotFoundException;
import com.flywithus.airlinereservations.exception.user.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserExceptionHandler {

    private static final String ARGUMENT_TYPE_MISMATCH = "Mismatched input type, parameter [ %s ] can not accept value [ %s ] as it is not of type [ %s ]";

    @ExceptionHandler(value = {UserServiceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserException(UserServiceException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, getValidationErrorsFromException(ex));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, new UserServiceException(null, String.format(ARGUMENT_TYPE_MISMATCH, ex.getParameter().getParameterName(), Objects.requireNonNull(ex.getValue()), ex.getParameter().getParameterType().getName())));
    }

    private List<ErrorDTO.InvalidFieldDTO> getValidationErrorsFromException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().map(error -> new ErrorDTO.InvalidFieldDTO(((FieldError) error).getField(), error.getDefaultMessage())).collect(Collectors.toList());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, Exception ex) {
        return new ResponseEntity<>(new ErrorDTO(status, ex), status);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, List<ErrorDTO.InvalidFieldDTO> validationErrors) {
        return new ResponseEntity<>(new ErrorDTO(status, validationErrors), status);
    }
}
