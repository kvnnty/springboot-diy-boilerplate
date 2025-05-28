package com.kvn.starter.v1.dtos.responses.auth;

import com.kvn.starter.v1.dtos.responses.users.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
  private UserResponseDTO user;
  private String token;
}
