package com.example.toj.controller;

import com.example.toj.service.storage.TempFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    TempFileStorageService fileStorageService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        var uuid = fileStorageService.store(file);

        return "{\"file_uuid\": \"%s\"}".formatted(uuid);
    }
}
