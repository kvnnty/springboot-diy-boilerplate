package com.kvn.starter.v1.repositories.roles;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.starter.v1.entities.user.Role;
import com.kvn.starter.v1.enums.user.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByType(ERole type);

  boolean existsByType(ERole type);
}