package com.flywithus.airlinereservations.mapper;

import com.flywithus.airlinereservations.dto.UserDTO;
import com.flywithus.airlinereservations.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper extends ModelMapper {

    public UserDTO convertToDto(User user) {
        return super.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDto) {
        return super.map(userDto, User.class);
    }

    public List<UserDTO> convertToDto(List<User> users) {
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
