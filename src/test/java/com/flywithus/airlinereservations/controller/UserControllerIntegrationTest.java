package com.flywithus.airlinereservations.controller;

import com.flywithus.airlinereservations.AirlineReservationsApplication;
import com.flywithus.airlinereservations.dto.UserDTO;
import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("UserController Integration Test")
@SpringBootTest(classes = AirlineReservationsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    private static final String LOCAL_URL = "http://localhost:%s/api%s";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Retrieves a single User")
    void getUserById() {
        User mockUser = user();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        ResponseEntity<UserDTO> response = this.restTemplate.getForEntity(url("/users/1"), UserDTO.class);

        UserDTO responseUser = response.getBody();

        assertSame(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseUser);

        UserDTO userDTO = userDTO();
        assertEquals(userDTO, responseUser);
    }

    @Test
    @DisplayName("Failure to retrieve an unknown User")
    void getUnknownUserById() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(url("/users/2"), String.class);

        assertSame(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("does not match any users"));
    }

    @Test
    @DisplayName("Register a User")
    void registerUser() {
        User mockUser = user();
        Mockito.when(userRepository.save(any())).thenReturn(mockUser);

        UserDTO userDTO = userDTO();
        ResponseEntity<UserDTO> response = this.restTemplate.postForEntity(url("/users"), httpEntity(userDTO), UserDTO.class);

        UserDTO responseUser = response.getBody();

        assertSame(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(responseUser);
        assertEquals(userDTO, responseUser);
    }

    @Test
    @DisplayName("Update a User")
    void updateUser() {
        User oldUser = user();
        oldUser.setUsername("georges.sand");

        User newUser = user();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));
        Mockito.when(userRepository.save(any())).thenReturn(newUser);

        UserDTO userDTO = userDTO();
        ResponseEntity<UserDTO> response = this.restTemplate.exchange(url("/users/1"), HttpMethod.PUT, httpEntity(userDTO), UserDTO.class);

        assertSame(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private User user() {
        return new User(1, "georges.brassens", LocalDate.of(1921, 10, 22), "FRA", "+33558643158", "M");
    }

    private UserDTO userDTO() {
        return new UserDTO(1, "georges.brassens", LocalDate.of(1921, 10, 22), "FRA", "+33558643158", "M");
    }

    private String url(String endPoint) {
        return String.format(LOCAL_URL, port, endPoint);
    }

    private HttpEntity<Object> httpEntity(Object requestBody) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestBody, headers);
    }
}
