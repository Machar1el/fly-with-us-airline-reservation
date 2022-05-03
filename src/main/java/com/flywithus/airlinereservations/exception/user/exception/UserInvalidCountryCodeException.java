package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserInvalidCountryCodeException extends UserException {

    private static final String USER_HAS_INVALID_COUNTRY_CODE = "Provided country code is invalid. Reason : ";

    public static final String COUNTRY_CODE_IS_NULL = "Provided country code is null or empty";
    public static final String COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY = "Provided country code does not match any country";
    public static final String COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION = "Provided country code is not allowed for registration";

    public UserInvalidCountryCodeException(User user, String message) {
        super(user, USER_HAS_INVALID_COUNTRY_CODE + message);
    }

}
