package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserInvalidUsernameException extends UserException {

    private static final String PROVIDED_USERNAME_IS_INVALID_REASON = "Provided username is invalid. Reason : ";

    public static final String USERNAME_DOES_NOT_EXIST = "Username is null or empty";
    public static final String USERNAME_HAS_WRONG_LENGTH = "Username should be between 3 to 30 characters long";

    public UserInvalidUsernameException(User user, String message) {
        super(user, PROVIDED_USERNAME_IS_INVALID_REASON + message);
    }
}
