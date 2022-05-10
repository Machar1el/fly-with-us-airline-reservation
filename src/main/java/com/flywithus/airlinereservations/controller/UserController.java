package com.flywithus.airlinereservations.controller;

import com.flywithus.airlinereservations.dto.ErrorDTO;
import com.flywithus.airlinereservations.dto.UserDTO;
import com.flywithus.airlinereservations.exception.user.exception.UserNotFoundException;
import com.flywithus.airlinereservations.exception.user.exception.UserServiceException;
import com.flywithus.airlinereservations.mapper.UserMapper;
import com.flywithus.airlinereservations.service.UserServiceImpl;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @Operation(summary = "Return all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all users",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))})
    })
    @GetMapping
    ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userMapper.convertToDto(userService.getUsers()), HttpStatus.OK);
    }

    @Operation(summary = "Return a user from its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the requested user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input (ID is malformed or empty)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Provided ID doesn't match any user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @GetMapping("/{id}")
    ResponseEntity<UserDTO> getUserById(@Parameter(description = "ID of the requested user. Cannot be empty.",
            required = true) @PathVariable @NotNull @Min(1) long id) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.convertToDto(userService.getUserById(id)));
    }

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input (some inputs are malformed or empty)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PostMapping
    ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO user) throws UserServiceException {
        return new ResponseEntity<>(userMapper.convertToDto(userService.createUser(userMapper.convertToEntity(user))), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the specified user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input (some inputs are malformed or empty)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Provided ID doesn't match any user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))})
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDTO> updateUser(@Parameter(description = "ID of the user you'd like to update. Cannot be empty.",
            required = true) @PathVariable @NotNull @Min(1) long id,
                                       @Valid @RequestBody UserDTO user) throws UserServiceException {

        user.setId(id);

        UserDTO updatedUser = userMapper.convertToDto(userService.updateUser(userMapper.convertToEntity(user)));

        return new ResponseEntity<>(updatedUser, HttpStatus.NO_CONTENT);
    }
}
