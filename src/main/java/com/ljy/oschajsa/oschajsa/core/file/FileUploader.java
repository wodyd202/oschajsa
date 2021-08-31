package com.ljy.oschajsa.oschajsa.core.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    String uploadFile(MultipartFile file, String fileName);
}
