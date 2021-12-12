package com.ljy.oschajsa.common.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    String uploadFile(MultipartFile file, String fileName);
    void removeFile(String logo);
}
