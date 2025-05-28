package com.kvn.starter.v1.utils.mappers;

import com.kvn.starter.v1.dtos.responses.users.UserResponseDTO;
import com.kvn.starter.v1.entities.user.User;

public class UserMapper {
  public static UserResponseDTO toDto(User user) {
    return UserResponseDTO.builder()
        .id(user.getId())
        .names(user.getNames())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .nationalId(user.getNationalId())
        .role(user.getRole().getType())
        .build();
  }
}
