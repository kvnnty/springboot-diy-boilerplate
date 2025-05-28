package com.kvn.starter.v1.repositories.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.starter.v1.entities.user.Role;
import com.kvn.starter.v1.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Optional<User> findByNationalId(String nationalId);

  Optional<User> findByPhoneNumber(String phoneNumber);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  boolean existsByNationalId(String nationalId);

  List<User> findAllByRole(Role role);
}
