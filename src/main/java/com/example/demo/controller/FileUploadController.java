package com.example.demo.controller;

import com.example.demo.component.OSSComponent;
import com.example.demo.util.TransferFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @Autowired
    OSSComponent ossComponent;
    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file")MultipartFile multipartFile)throws IOException {
        File file = TransferFile.transMultipartFile(multipartFile, "ww",".jpg","D:/ww/file");
        String fileName = UUID.randomUUID().toString().replace("-","");
        String url = ossComponent.uploadImage(file,fileName);
    }
}
