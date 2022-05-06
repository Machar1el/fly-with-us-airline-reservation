package com.flywithus.airlinereservations.controller;

import com.flywithus.airlinereservations.model.User;
import com.flywithus.airlinereservations.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Return all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class))) })
    })
    @GetMapping
    Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Return a user from its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the requested user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input (ID is malformed or empty)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @GetMapping("/{id}")
    Optional<User> getUserById(@Parameter(description = "ID of the requested user. Cannot be empty.",
            required = true) @PathVariable @NotBlank long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input (some inputs are malformed or empty)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @PostMapping
    User registerUser(@RequestBody @NotNull User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Updates a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the specified user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input (some inputs are malformed or empty)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @PutMapping("/{id}")
    ResponseEntity<User> putUser(@Parameter(description = "ID of the user you'd like to update. Cannot be empty.",
            required = true) @PathVariable @NotBlank long id,
                                 @RequestBody @NotNull @Valid User user) {
        return (!userService.existsById(id))
                ? new ResponseEntity<>(userService.createUser(user),
                HttpStatus.CREATED)
                : new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }
}
