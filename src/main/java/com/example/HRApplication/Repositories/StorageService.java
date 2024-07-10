package com.example.HRApplication.Repositories;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    List<String> listAllFiles();

    Resource loadAsResource(String filename);

    void delete(String filename);

    void deleteAll();
}
