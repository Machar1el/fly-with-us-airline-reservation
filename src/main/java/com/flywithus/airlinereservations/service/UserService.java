package com.flywithus.airlinereservations.service;

import com.flywithus.airlinereservations.aspect.monitoring.Monitor;
import com.flywithus.airlinereservations.exception.user.exception.UserNotFoundException;
import com.flywithus.airlinereservations.model.User;

import java.util.List;

public interface UserService {
    
    @Monitor(threshold = 25)
    List<User> getUsers();

    boolean existsById(long id);

    @Monitor(threshold = 2)
    User getUserById(long id) throws UserNotFoundException;

    @Monitor(threshold = 8)
    User createUser(User user);

    User updateUser(User user);
}
