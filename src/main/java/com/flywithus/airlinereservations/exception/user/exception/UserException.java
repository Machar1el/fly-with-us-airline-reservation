package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

import java.util.Objects;

public abstract class UserException extends Exception {

    private static final String ERROR_PROCESSING_USER_X_BECAUSE = "Error while handling user of ID (%s) : %s";

    protected UserException (User user, String cause) {
        super(String.format(ERROR_PROCESSING_USER_X_BECAUSE, handleNullUser(user), cause));
    }

    private static String handleNullUser(User user) {
        return Objects.nonNull(user) ? String.valueOf(user.getId()) : "x";
    }
}
