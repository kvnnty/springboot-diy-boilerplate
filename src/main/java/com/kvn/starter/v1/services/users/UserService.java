package com.kvn.starter.v1.services.users;

import com.kvn.starter.v1.dtos.requests.user.ChangePasswordDTO;
import com.kvn.starter.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.starter.v1.dtos.requests.user.UpdateUserDTO;
import com.kvn.starter.v1.entities.user.User;

public interface UserService {
  void registerCustomer(CreateUserDTO requestDTO);

  User getCurrentUser();

  void updateUserProfile(UpdateUserDTO dto);

  void changePassword(ChangePasswordDTO dto);

  void deleteCurrentUser();

}
