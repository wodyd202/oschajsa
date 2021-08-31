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

    private final String EMPTY_MESSAGE = "file extention must not be empty";
    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        String originalFileName = file.getOriginalFilename(); // 오리지날 파일명
        if (originalFileName.equals("")) {
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
        String savedFileName = fileName + extension; // 저장될 파일 명
        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
            e.printStackTrace();
        }
        return savedFileName;
    }
}
