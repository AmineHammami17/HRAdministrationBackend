package com.example.HRApplication.Services;

import com.example.HRApplication.Exceptions.StorageException;
import com.example.HRApplication.Exceptions.StorageFileNotFoundException;
import com.example.HRApplication.Repositories.StorageService;
import com.example.HRApplication.Models.FileEntity;
import com.example.HRApplication.Repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemStorageService implements StorageService {

    private final FileRepository fileRepository;

    @Autowired
    public FileSystemStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void init() {
        // Initialize if needed
    }

    @Override
    @Transactional
    public void store(MultipartFile file) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> listAllFiles() {
        return fileRepository.findAll().stream()
                .map(FileEntity::getFilename)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Resource loadAsResource(String filename) {
        FileEntity fileEntity = fileRepository.findByFilename(filename);
        if (fileEntity == null) {
            throw new StorageFileNotFoundException("File not found: " + filename);
        }
        return new ByteArrayResource(fileEntity.getData());
    }



    @Override
    @Transactional
    public void delete(String filename) {
        FileEntity fileEntity = fileRepository.findByFilename(filename);
        if (fileEntity != null) {
            fileRepository.delete(fileEntity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        fileRepository.deleteAll();
    }


}
