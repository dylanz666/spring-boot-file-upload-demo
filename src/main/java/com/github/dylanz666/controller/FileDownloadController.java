package com.github.dylanz666.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author : dylanz
 * @since : 11/08/2020
 */
@RestController
@RequestMapping("/api")
public class FileDownloadController {
    @Value(value = "${file.download.dir}")
    private String rootPath;

    private FileInputStream fis = null;
    private BufferedInputStream bis = null;

    @GetMapping("/download/ping")
    public String ping() {
        return "success";
    }

    @GetMapping("/file")
    public String download(@RequestParam String fileName) {
        try {
            File file = new File(rootPath, fileName);

            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            assert response != null;
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            if (file.exists()) {
                byte[] buffer = new byte[1024];
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();

                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
