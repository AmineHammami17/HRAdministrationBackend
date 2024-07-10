package com.example.HRApplication.Repositories;

import com.example.HRApplication.Models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByFilename(String filename);
}
