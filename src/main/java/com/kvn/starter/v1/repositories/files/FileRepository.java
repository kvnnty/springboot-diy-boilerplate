package com.kvn.starter.v1.repositories.files;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.starter.v1.entities.files.StoredFile;

@Repository
public interface FileRepository extends JpaRepository<StoredFile, UUID> {

}
