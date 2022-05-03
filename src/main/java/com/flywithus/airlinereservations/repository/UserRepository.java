package com.flywithus.airlinereservations.repository;

import com.flywithus.airlinereservations.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
