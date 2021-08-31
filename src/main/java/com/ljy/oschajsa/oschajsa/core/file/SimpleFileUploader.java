package com.ljy.oschajsa.oschajsa.core.file;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class SimpleFileUploader implements FileUploader{

    @Value("${file-upload.url}")
    private String fileRoot;

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        File targetFile = new File(fileRoot + fileName);
        try {
            InputStream fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
            e.printStackTrace();
        }
        return fileName;
    }
}
