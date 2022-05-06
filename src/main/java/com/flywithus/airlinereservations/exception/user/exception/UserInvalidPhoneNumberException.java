package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserInvalidPhoneNumberException extends UserException {

    private static final String PROVIDED_PHONE_NUMBER_IS_INVALID_REASON = "Provided phone number (%s) is invalid because it doesn't match this pattern : +[0-9]{10,13} (example : +33512346578) ";

    public UserInvalidPhoneNumberException(User user) {
        super(user, String.format(PROVIDED_PHONE_NUMBER_IS_INVALID_REASON, user.getPhoneNumber()));
    }
}
