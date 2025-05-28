package com.kvn.starter.v1.services.admin.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kvn.starter.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.starter.v1.dtos.requests.user.UpdateUserDTO;
import com.kvn.starter.v1.dtos.responses.users.UserResponseDTO;
import com.kvn.starter.v1.entities.user.Role;
import com.kvn.starter.v1.entities.user.User;
import com.kvn.starter.v1.enums.user.ERole;
import com.kvn.starter.v1.exceptions.DuplicateResourceException;
import com.kvn.starter.v1.exceptions.ResourceNotFoundException;
import com.kvn.starter.v1.repositories.roles.RoleRepository;
import com.kvn.starter.v1.repositories.users.UserRepository;
import com.kvn.starter.v1.services.admin.AdminService;
import com.kvn.starter.v1.utils.mappers.ModelMapperConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void createAdmin(CreateUserDTO requestDto) {
    if (userRepository.existsByEmail(requestDto.getEmail())) {
      throw new DuplicateResourceException("Email is already registered");
    }
    if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
      throw new DuplicateResourceException("Phone number is already registered");
    }
    if (userRepository.existsByNationalId(requestDto.getNationalId())) {
      throw new DuplicateResourceException("National ID is already registered");
    }

    Role adminRole = roleRepository.findByType(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new IllegalStateException("Admin role not found"));

    User user = User.builder()
        .names(requestDto.getNames())
        .email(requestDto.getEmail())
        .phoneNumber(requestDto.getPhoneNumber())
        .nationalId(requestDto.getNationalId())
        .password(passwordEncoder.encode(requestDto.getPassword()))
        .isVerified(true)
        .role(adminRole)
        .build();

    userRepository.save(user);
  }

  @Override
  public void deleteAdmin(UUID adminId) {
    User user = userRepository.findById(adminId)
        .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));

    if (!user.getRole().getType().equals(ERole.ROLE_ADMIN)) {
      throw new IllegalArgumentException("User is not an admin");
    }

    userRepository.delete(user);
  }

  @Override
  public List<UserResponseDTO> getAllAdmins() {
    Role adminRole = roleRepository.findByType(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new IllegalStateException("Admin role not found"));

    List<User> admins = userRepository.findAllByRole(adminRole);
    return admins.stream()
        .map(user -> ModelMapperConfig.map(user, UserResponseDTO.class))
        .toList();
  }

  @Override
  public void updateAdmin(UUID adminId, UpdateUserDTO updateUserDTO) {
    User user = userRepository.findById(adminId)
        .orElseThrow(() -> new ResourceNotFoundException("Admin user not found"));

    if (!user.getRole().getType().equals(ERole.ROLE_ADMIN)) {
      throw new IllegalArgumentException("User is not an admin");
    }

    if (updateUserDTO.getNames() != null) {
      user.setNames(updateUserDTO.getNames());
    }
    if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().equals(user.getEmail())) {
      if (userRepository.existsByEmail(updateUserDTO.getEmail())) {
        throw new DuplicateResourceException("Email is already registered");
      }
      user.setEmail(updateUserDTO.getEmail());
    }
    if (updateUserDTO.getPhoneNumber() != null && !updateUserDTO.getPhoneNumber().equals(user.getPhoneNumber())) {
      if (userRepository.existsByPhoneNumber(updateUserDTO.getPhoneNumber())) {
        throw new DuplicateResourceException("Phone number is already registered");
      }
      user.setPhoneNumber(updateUserDTO.getPhoneNumber());
    }
    if (updateUserDTO.getNationalId() != null && !updateUserDTO.getNationalId().equals(user.getNationalId())) {
      if (userRepository.existsByNationalId(updateUserDTO.getNationalId())) {
        throw new DuplicateResourceException("National ID is already registered");
      }
      user.setNationalId(updateUserDTO.getNationalId());
    }

    userRepository.save(user);
  }
}
