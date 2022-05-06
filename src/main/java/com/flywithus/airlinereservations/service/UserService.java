package com.flywithus.airlinereservations.service;

import com.flywithus.airlinereservations.aspect.monitoring.Monitor;
import com.flywithus.airlinereservations.exception.user.exception.UserInvalidPhoneNumberException;
import com.flywithus.airlinereservations.exception.user.exception.*;
import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private static final int USERNAME_TOO_SHORT_THRESHOLD = 3;
    private static final int USERNAME_TOO_LONG_THRESHOLD = 30;

    private static final String PHONE_NUMBER_REGEX_PATTERN = "^\\+[0-9]{10,13}";

    @Monitor(threshold = 25)
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Monitor(threshold = 2)
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Monitor(threshold = 8)
    public User createUser(User user) {
        assertUserIsValid(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        assertUserIsValid(user);
        return userRepository.save(user);
    }

    private void assertUserIsValid(User user) {
        assertUsernameIsValid(user);
        assertUserBirthDateIsValid(user);
        assertUserCountryIsValid(user);
        assertPhoneNumberIsValid(user);
    }

    @SneakyThrows
    private void assertPhoneNumberIsValid(User user) {
        if (Objects.nonNull(user.getPhoneNumber())) {
            if (!user.getPhoneNumber().matches(PHONE_NUMBER_REGEX_PATTERN)) throw new UserInvalidPhoneNumberException(user);
        }
    }

    @SneakyThrows
    private void assertUsernameIsValid(User user) {
        if (!StringUtils.hasLength(user.getUsername())) throw new UserInvalidUsernameException(user, UserInvalidUsernameException.USERNAME_DOES_NOT_EXIST);
        if (user.getUsername().length() < USERNAME_TOO_SHORT_THRESHOLD) throw new UserInvalidUsernameException(user, UserInvalidUsernameException.USERNAME_HAS_WRONG_LENGTH);
        if (user.getUsername().length() > USERNAME_TOO_LONG_THRESHOLD) throw new UserInvalidUsernameException(user, UserInvalidUsernameException.USERNAME_HAS_WRONG_LENGTH);
    }

    @SneakyThrows
    private void assertUserBirthDateIsValid(User user) {
        if (Objects.isNull(user.getBirthDate())) throw new UserInvalidBirthdateException(user, UserInvalidBirthdateException.PROVIDED_BIRTHDATE_IS_NULL_OR_EMPTY);
    }

    @SneakyThrows
    private void assertUserCountryIsValid(User user) {
        if (StringUtils.hasLength(user.getCountry())) {
            assertUserCountryExists(user);
            assertUserCountryIsAuthorized(user);
        } else {
            throw new UserInvalidCountryCodeException(user, UserInvalidCountryCodeException.COUNTRY_CODE_IS_NULL);
        }
    }

    @SneakyThrows
    private void assertUserCountryExists(User user) {
        if (!Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(user.getCountry())) throw new UserInvalidCountryCodeException(user, UserInvalidCountryCodeException.COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY);
    }

    @SneakyThrows
    private void assertUserCountryIsAuthorized(User user) {
        if (!Objects.equals(user.getCountry(), "FRA")) throw new UserInvalidCountryCodeException(user, UserInvalidCountryCodeException.COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION);
    }
}
