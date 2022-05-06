package com.flywithus.airlinereservations.exception.user.exception;

public class UserNotFoundException extends UserException {

    private static final String NO_USER_FOUND_FOR_ID = "Provided ID (%d) does not match any users";

    public UserNotFoundException(long id) {
        super(null, String.format(NO_USER_FOUND_FOR_ID, id));
    }
}
