package com.flywithus.airlinereservations.exception.user.exception;

public class UserNotFoundException extends UserServiceException {

    public UserNotFoundException(long id) {
        super(null, String.format(UserServiceException.NO_USER_FOUND_FOR_ID, id));
    }
}
