package com.flywithus.airlinereservations.exception.user.exception;

import com.flywithus.airlinereservations.model.User;

import java.util.Objects;

public class UserServiceException extends Exception {

    private static final String ERROR_PROCESSING_USER_X_BECAUSE = "Error while handling user of ID (%s) : %s";

    public static final String NO_USER_FOUND_FOR_ID = "Provided ID (%s) does not match any users";
    public static final String USERNAME_DOES_NOT_EXIST = "Username is null or empty";
    public static final String USERNAME_HAS_WRONG_LENGTH = "Username should be between 3 to 30 characters long";
    public static final String PROVIDED_PHONE_NUMBER_IS_INVALID_REASON = "Provided phone number is invalid because it doesn't match this pattern : +[0-9]{10,13} (example : +33512346578) ";
    public static final String PROVIDED_GENDER_DOES_NOT_MATCH_PATTERN = "Provided gender does not match pattern [A-Z]";
    public static final String COUNTRY_CODE_IS_NULL = "Provided country code is null or empty";
    public static final String COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY = "Provided country code does not match any country";
    public static final String COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION = "Provided country code is not allowed for registration";
    public static final String PROVIDED_BIRTHDATE_IS_NULL_OR_EMPTY = "Provided birth date is null or empty";
    public static final String PROVIDED_BIRTHDATE_IS_UNDERAGE = "Provided birth date is under the age of 18 years old and thus can't be registered with";
    public static final String PROVIDED_DATE_MUST_MATCH_PATTERN_YYYY_MM_DD = "Provided date must match pattern 'yyyy-MM-dd'";
    public static final String PROVIDED_USERNAME_ALREADY_EXISTS = "Provided username is already taken";

    public UserServiceException(User user, String cause) {
        super(String.format(ERROR_PROCESSING_USER_X_BECAUSE, handleNullUser(user), cause));
    }

    private static String handleNullUser(User user) {
        return Objects.nonNull(user) ? String.valueOf(user.getId()) : "x";
    }
}
