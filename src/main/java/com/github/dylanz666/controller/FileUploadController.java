package com.github.dylanz666.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author : dylanz
 * @since : 11/02/2020
 */
@RestController
@RequestMapping("/api")
public class FileUploadController {
    @Value(value = "${file.upload.dir}")
    private String rootPath;

    @GetMapping("/upload/ping")
    public String ping() {
        return "success";
    }

    @PostMapping("/file")
    public Boolean upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            System.out.println(fileName);

            File dest = new File(rootPath, fileName);
            file.transferTo(dest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
