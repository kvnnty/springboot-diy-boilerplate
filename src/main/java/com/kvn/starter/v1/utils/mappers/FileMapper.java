package com.kvn.starter.v1.utils.mappers;

import com.kvn.starter.v1.dtos.responses.files.FileUploadResponseDTO;
import com.kvn.starter.v1.entities.files.StoredFile;

public class FileMapper {
  public static FileUploadResponseDTO toDto(StoredFile file) {
    return FileUploadResponseDTO.builder()
        .url(file.getUrl())
        .contentType(file.getContentType())
        .size(file.getSize())
        .build();
  }
}
