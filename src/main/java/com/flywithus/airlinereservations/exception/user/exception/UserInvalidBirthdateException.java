package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

public class UserInvalidBirthdateException extends UserException {

    private static final String PROVIDED_BIRTHDATE_IS_INVALID_REASON = "Provided birth date is invalid. Reason : ";

    public static final String PROVIDED_BIRTHDATE_IS_NULL_OR_EMPTY = "Provided birth date is null or empty";
    public static final String PROVIDED_BIRTHDATE_IS_UNDERAGE = "Provided birth date is under the age of 18 years old and thus can't be registered with";
    public static final String PROVIDED_DATE_MUST_MATCH_PATTERN_YYYY_MM_DD = "Provided date must match pattern 'yyyy-MM-dd'";

    public UserInvalidBirthdateException(User user, String reason) {
        super(user, PROVIDED_BIRTHDATE_IS_INVALID_REASON + reason);
    }
}
