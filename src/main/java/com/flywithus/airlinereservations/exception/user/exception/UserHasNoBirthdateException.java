package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserHasNoBirthdateException extends UserException {

    private static final String USER_HAS_NO_BIRTHDATE = "This user has no birthdate and thus can't be created";

    public UserHasNoBirthdateException(User user) {
        super(user, USER_HAS_NO_BIRTHDATE);
    }
}
