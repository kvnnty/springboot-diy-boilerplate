package com.kvn.starter.v1.services.users.impl;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kvn.starter.v1.dtos.requests.user.ChangePasswordDTO;
import com.kvn.starter.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.starter.v1.dtos.requests.user.UpdateUserDTO;
import com.kvn.starter.v1.entities.user.Role;
import com.kvn.starter.v1.entities.user.User;
import com.kvn.starter.v1.enums.user.ERole;
import com.kvn.starter.v1.exceptions.BadRequestException;
import com.kvn.starter.v1.exceptions.DuplicateResourceException;
import com.kvn.starter.v1.repositories.roles.RoleRepository;
import com.kvn.starter.v1.repositories.users.UserRepository;
import com.kvn.starter.v1.security.user.UserPrincipal;
import com.kvn.starter.v1.services.users.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerCustomer(CreateUserDTO requestDTO) {
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new DuplicateResourceException("Email is already in use.");
    }
    if (userRepository.existsByPhoneNumber(requestDTO.getPhoneNumber())) {
      throw new DuplicateResourceException("Phone number is already in use.");
    }
    if (userRepository.existsByNationalId(requestDTO.getNationalId())) {
      throw new DuplicateResourceException("National ID is already registered.");
    }

    Role customerRole = roleRepository.findByType(ERole.ROLE_USER)
        .orElseThrow(() -> new IllegalStateException("Role not found"));

    User newCustomer = User.builder()
        .names(requestDTO.getNames())
        .email(requestDTO.getEmail())
        .password(passwordEncoder.encode(requestDTO.getPassword()))
        .phoneNumber(requestDTO.getPhoneNumber())
        .nationalId(requestDTO.getNationalId())
        .role(customerRole)
        .build();

    userRepository.save(newCustomer);

  }

  @Override
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null) {
      throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
    }

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    User user = userRepository.findByEmail(userPrincipal.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return user;
  }

  @Override
  @Transactional
  public void updateUserProfile(UpdateUserDTO dto) {
    User user = getCurrentUser();

    if (!dto.getPhoneNumber().equals(user.getPhoneNumber())) {
      throw new BadRequestException("The new phone number cannot be the same as your current one.");
    }

    if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
      throw new DuplicateResourceException("Phone number is already in use.");
    }

    user.setNames(dto.getNames() != null ? dto.getNames() : user.getNames());
    user.setPhoneNumber(dto.getPhoneNumber() != null ? dto.getPhoneNumber() : user.getPhoneNumber());

    userRepository.save(user);
  }

  @Override
  public void changePassword(ChangePasswordDTO dto) {
    User user = getCurrentUser();

    if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
      throw new BadCredentialsException("Current password is incorrect.");
    }

    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteCurrentUser() {
    User user = getCurrentUser();
    userRepository.delete(user);
  }

}
