package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserInvalidGenderException extends UserException {

    private static final String PROVIDED_GENDER_DOES_NOT_MATCH_PATTERN = "Provided gender (%s) does not match pattern [A-Z]";

    public UserInvalidGenderException(User user) {
        super(user, String.format(PROVIDED_GENDER_DOES_NOT_MATCH_PATTERN, user.getGender()));
    }
}
