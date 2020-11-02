package com.github.dylanz666.controller;

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
    private static final String rootPath = "E:\\Resource\\spring-boot-cloud\\";

    @GetMapping("/ping")
    public String ping() {
        return "success";
    }

    @PostMapping("/file")
    public Boolean upload(@RequestParam("file") MultipartFile file) {
        try{
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);

            File dest = new File(rootPath + fileName);
            file.transferTo(dest);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
