package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserAlreadyExistsException extends UserException {

    private static final String PROVIDED_USERNAME_ALREADY_EXISTS = "Provided username is already taken";

    public UserAlreadyExistsException(User user) {
        super(user, PROVIDED_USERNAME_ALREADY_EXISTS);
    }
}
