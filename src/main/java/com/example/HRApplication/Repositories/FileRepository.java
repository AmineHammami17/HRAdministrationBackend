package com.example.HRApplication.FileStorage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFilename(String filename);
}
