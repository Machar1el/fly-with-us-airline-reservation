package com.flywithus.airlinereservations.service;

import com.flywithus.airlinereservations.exception.user.exception.UserHasNoBirthdateException;
import com.flywithus.airlinereservations.exception.user.exception.UserInvalidCountryCodeException;
import com.flywithus.airlinereservations.exception.user.exception.UserInvalidUsernameException;
import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service")
class UserServiceTest {

    private static final String USERNAME_DOES_NOT_EXIST = "Username is null or empty";
    private final static String USERNAME_HAS_WRONG_LENGTH = "Username should be between 3 to 30 characters long";

    private static final String USER_HAS_NO_BIRTHDATE = "This user has no birthdate and thus can't be created";

    private static final String COUNTRY_CODE_IS_NULL = "Provided country code is null or empty";
    private static final String COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY = "Provided country code does not match any country";
    private static final String COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION = "Provided country code is not allowed for registration";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private static List<User> users;

    @Test
    @DisplayName("Retrieves a collection of Users")
    void getUsers() {
        buildUserList();

        Mockito.when(userRepository.findAll()).thenReturn(users);

        assertTrue(userService.getUsers().iterator().hasNext());
    }

    @Test
    @DisplayName("Checks for a Users' existence")
    void existsById() {
        buildUserList();

        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        assertTrue(userService.existsById(1L));
    }

    @Test
    @DisplayName("Retrieves a single User")
    void getUserById() {
        buildUserList();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(1)));

        Optional<User> user = userService.getUserById(1);

        assertTrue(user.isPresent());
        assertEquals(user.get(), users.get(1));
    }

    @Test
    @DisplayName("Creates a User")
    void createUser() {
        User user = user();

        Mockito.when(userRepository.save(any())).thenReturn(user);

        assertEquals(user, userService.createUser(user));
    }

    @Test
    @DisplayName("Creates a User with no phone number or gender")
    void createUserWithNoPhoneNumberOrGender() {
        User user = userWithNoPhoneNumberOrGender();

        Mockito.when(userRepository.save(any())).thenReturn(user);

        assertEquals(user, userService.createUser(user));
    }

    @Test
    @DisplayName("Creates a User with no username")
    void createUserWithNoUsername() {
        Exception exception = assertThrows(UserInvalidUsernameException.class, () -> userService.createUser(userWithNoUsername()));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(USERNAME_DOES_NOT_EXIST));
    }

    @Test
    @DisplayName("Creates a User with a username shorter than accepted")
    void createUserWithVeryShortUsername() {
        User user = user();
        user.setUsername("Ho");

        Exception exception = assertThrows(UserInvalidUsernameException.class, () -> userService.createUser(user));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(USERNAME_HAS_WRONG_LENGTH));
    }

    @Test
    @DisplayName("Creates a User with a username longer than accepted")
    void createUserWithVeryLongUsername() {
        User user = user();
        user.setUsername("pierre-jean.capot.paganel.belmon.de.cuissac");

        Exception exception = assertThrows(UserInvalidUsernameException.class, () -> userService.createUser(user));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(USERNAME_HAS_WRONG_LENGTH));
    }

    @Test
    @DisplayName("Creates a User with no birthdate")
    void createUserWithNoBirthdate() {
        Exception exception = assertThrows(UserHasNoBirthdateException.class, () -> userService.createUser(userWithNoBirthdate()));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(USER_HAS_NO_BIRTHDATE));
    }

    @Test
    @DisplayName("Creates a User with no country")
    void createUserWithNoCountry() {
        Exception exception = assertThrows(UserInvalidCountryCodeException.class, () -> userService.createUser(userWithNoCountry()));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(COUNTRY_CODE_IS_NULL));
    }

    @Test
    @DisplayName("Creates a User with a country code that doesn't match any country")
    void createUserWithUnknownCountry() {
        Exception exception = assertThrows(UserInvalidCountryCodeException.class, () -> userService.createUser(userWithUnknownCountry()));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(COUNTRY_CODE_DOES_NOT_MATCH_ANY_COUNTRY));
    }

    @Test
    @DisplayName("Creates a User with an unauthorized nationality")
    void createUserWithUnauthorizedNationality() {
        Exception exception = assertThrows(UserInvalidCountryCodeException.class, () -> userService.createUser(userWithUnauthorizedCountry()));

        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(COUNTRY_CODE_IS_NOT_ALLOWED_FOR_REGISTRATION));
    }

    @Test
    @DisplayName("Updates a User")
    void updateUser() {
        User oldUser = user();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        User user = userService.getUserById(1).orElseThrow();
        assertEquals(oldUser, user);
        
        User newUser = newUser();
        Mockito.when(userRepository.save(any())).thenReturn(newUser);
        assertNotEquals(oldUser, userService.updateUser(newUser));
    }

    private void buildUserList() {
        users = new ArrayList<>();
        users.add(new User(1, "georges.brassens", LocalDate.of(1921, 10,22), "FRA", "+33558643158", "M"));
        users.add(new User(2, "gilbert.montagné", LocalDate.of(1951,12,28), "FRA", "+33512346578", "M"));
    }

    private User user() {
        return new User(1, "georges.brassens", LocalDate.of(1921, 10,22), "FRA", "+33558643158", "M");
    }

    private User userWithNoPhoneNumberOrGender() {
        return new User(1, "georges.brassens", LocalDate.of(1921, 10,22), "FRA", null, null);
    }

    private User newUser() {
        return new User(1, "maxime.leforestier", LocalDate.of(1949, 2,10), "FRA", "+33556743118", "M");
    }

    private User userWithNoUsername() {
        return new User(1, null, LocalDate.of(1921, 10,22), "FRA", "+33558643158", "M");
    }

    private User userWithNoBirthdate() {
        return new User(1, "georges.brassens", null, "FRA", "+33558643158", "M");
    }

    private User userWithNoCountry() {
        return new User(1, "georges.brassens", LocalDate.of(1921, 10,22), "", "+33558643158", "M");
    }

    private User userWithUnknownCountry() {
        return new User(1, "georges.brassens", LocalDate.of(1921, 10,22), "GZT", "+33558643158", "M");
    }

    private User userWithUnauthorizedCountry() {
        return new User(1, "ron.weasley", LocalDate.of(1989, 3,18), "GBR", "+33558643158", "M");
    }
}