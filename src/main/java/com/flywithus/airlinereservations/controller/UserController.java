package com.flywithus.airlinereservations.controller;

import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    Optional<User> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    User registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> putUser(@PathVariable long id,
                                 @RequestBody User user) {
        return (!userService.existsById(id))
                ? new ResponseEntity<>(userService.createUser(user),
                HttpStatus.CREATED)
                : new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }
}
