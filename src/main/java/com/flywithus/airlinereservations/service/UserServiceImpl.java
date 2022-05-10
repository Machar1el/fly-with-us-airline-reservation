package com.flywithus.airlinereservations.service;

import com.flywithus.airlinereservations.aspect.monitoring.Monitor;
import com.flywithus.airlinereservations.exception.user.exception.UserNotFoundException;
import com.flywithus.airlinereservations.exception.user.exception.UserServiceException;
import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final int ADULT_AGE = 18;

    private static final int USERNAME_TOO_SHORT_THRESHOLD = 3;
    private static final int USERNAME_TOO_LONG_THRESHOLD = 30;

    public static final String PHONE_NUMBER_REGEX_PATTERN = "^\\+[0-9]{10,13}";
    public static final String GENDER_REGEX_PATTERN = "^[A-Z]";

    @Override
    @Monitor(threshold = 25)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    @Monitor(threshold = 2)
    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Monitor(threshold = 8)
    public User createUser(User user) throws UserServiceException {
        assertUserIsValid(user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws UserServiceException {
        assertUserExists(user);
        assertUserIsValid(user);
        return userRepository.save(user);
    }

    private void assertUserIsValid(User user) throws UserServiceException {
        assertUsernameIsValid(user);
        assertUsernameIsFree(user);
        assertUserBirthDateIsValid(user);
        assertUserCountryIsValid(user);
        assertPhoneNumberIsValid(user);
        assertUserGenderIsValid(user);
    }

    private void assertUsernameIsFree(User user) throws UserServiceException {
        if (userRepository.existsUserByUsername(user.getUsername()))
            throw new UserServiceException(user, UserServiceException.PROVIDED_USERNAME_ALREADY_EXISTS);
    }

    private void assertUserExists(User user) throws UserNotFoundException {
        if (existsById(user.getId())) throw new UserNotFoundException(user.getId());
    }

    private void assertPhoneNumberIsValid(User user) throws UserServiceException {
        if (Objects.nonNull(user.getPhoneNumber()) && !user.getPhoneNumber().matches(PHONE_NUMBER_REGEX_PATTERN))
            throw new UserServiceException(user, UserServiceException.PROVIDED_PHONE_NUMBER_IS_INVALID_REASON);
    }

    private void assertUserGenderIsValid(User user) throws UserServiceException {
        if (Objects.nonNull(user.getGender()) && !user.getGender().matches(GENDER_REGEX_PATTERN))
            throw new UserServiceException(user, UserServiceException.PROVIDED_GENDER_DOES_NOT_MATCH_PATTERN);
    }

    private void assertUsernameIsValid(User user) throws UserServiceException {
        if (!StringUtils.hasLength(user.getUsername()))
            throw new UserServiceException(user, UserServiceException.USERNAME_DOES_NOT_EXIST);
        if (user.getUsername().length() < USERNAME_TOO_SHORT_THRESHOLD)
            throw new UserServiceException(user, UserServiceException.USERNAME_HAS_WRONG_LENGTH);
        if (user.getUsername().length() > USERNAME_TOO_LONG_THRESHOLD)
            throw new UserServiceException(user, UserServiceException.USERNAME_HAS_WRONG_LENGTH);
    }

    private void assertUserBirthDateIsValid(User user) throws UserServiceException {
        if (Objects.isNull(user.getBirthDate())) {
            throw new UserServiceException(user, UserServiceException.PROVIDED_BIRTHDATE_IS_NULL_OR_EMPTY);
        } else {
            if (user.getBirthDate().until(LocalDate.now()).getYears() < ADULT_AGE)
                throw new UserServiceException(user, UserServiceException.PROVIDED_BIRTHDATE_IS_UNDERAGE);
        }
    }

    private void assertUserCountryIsValid(User user) throws UserServiceException {
        if (StringUtils.hasLength(user.getCountry())) {
            assertUserCountryExists(user);
            assertUserCountryIsAuthorized(user);
        } else {
            throw new UserServiceException(user, UserServiceException.COUNTRY_CODE_IS_NULL);
        }
    }

    @SneakyThrows
    private void assertUserCountryExists(User user) {
        if (!Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(user.getCountry()))
            throw new UserServiceException(user, UserServiceException.COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY);
    }

    @SneakyThrows
    private void assertUserCountryIsAuthorized(User user) {
        if (!Objects.equals(user.getCountry(), "FRA"))
            throw new UserServiceException(user, UserServiceException.COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION);
    }
}
