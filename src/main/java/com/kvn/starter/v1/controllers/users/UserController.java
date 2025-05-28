package com.kvn.starter.v1.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kvn.starter.v1.dtos.requests.user.*;
import com.kvn.starter.v1.entities.user.User;
import com.kvn.starter.v1.payload.ApiResponse;
import com.kvn.starter.v1.services.users.UserService;
import com.kvn.starter.v1.dtos.requests.user.UpdateUserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "APIs for managing user account related operations")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> registerCustomer(@Valid @RequestBody CreateUserDTO requestDTO) {
    userService.registerCustomer(requestDTO);
    return ApiResponse.success("Account created successfully, please check your email to verify your account",
        HttpStatus.CREATED, null);
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<User>> getCurrentUser() {
    User user = userService.getCurrentUser();
    return ApiResponse.success("User profile retrieved", HttpStatus.OK, user);
  }

  @PutMapping("/update-profile")
  public ResponseEntity<ApiResponse<Object>> updateProfile(@Valid @RequestBody UpdateUserDTO updateDTO) {
    userService.updateUserProfile(updateDTO);
    return ApiResponse.success("Profile updated successfully", HttpStatus.OK, null);
  }

  @PostMapping("/change-password")
  public ResponseEntity<ApiResponse<Object>> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
    userService.changePassword(dto);
    return ApiResponse.success("Password changed successfully", HttpStatus.OK, null);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ApiResponse<Object>> deleteAccount() {
    userService.deleteCurrentUser();
    return ApiResponse.success("Account deleted successfully", HttpStatus.OK, null);
  }
}
