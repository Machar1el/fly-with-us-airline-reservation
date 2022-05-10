package com.flywithus.airlinereservations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private int httpStatus;
    private String message;
    private List<InvalidFieldDTO> invalidFields;

    public ErrorDTO(HttpStatus status) {
        this.httpStatus = status.value();
        this.message = "Unexpected error";
    }

    public ErrorDTO(HttpStatus status, Throwable ex) {
        this.httpStatus = status.value();
        this.message = ex.getMessage();
    }

    public ErrorDTO(HttpStatus status, List<InvalidFieldDTO> invalidFields) {
        this.httpStatus = status.value();
        this.message = "Some inputs are invalid";
        this.invalidFields = invalidFields;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvalidFieldDTO {
        private String fieldName;
        private String reason;
    }
}
