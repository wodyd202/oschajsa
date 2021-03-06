package com.ljy.oschajsa.services.store.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Logo {
    private final String image;
    private Logo(String path) {
        if(Objects.isNull(path)){
            image = null;
            return;
        }else{
            verifyImageFile(path);
            image = UUID.randomUUID() + getExtention(path);
        }
    }

    private static final String ONLY_IMAGE_FILES_ARE_ACCEPTED_FOR_LOGOS = "업체 로고은 이미지 파일만 허용합니다.";
    private static final List<String> ALLOWED_EXTENTIONS = Arrays.asList(".png",".jpg",".jpeg");
    private void verifyImageFile(String path) {
        String extention = getExtention(path);
        if(!ALLOWED_EXTENTIONS.contains(extention)){
            throw new IllegalArgumentException(ONLY_IMAGE_FILES_ARE_ACCEPTED_FOR_LOGOS);
        }
    }

    private String getExtention(String path) {
        int dotIdx = path.lastIndexOf(".");
        if(dotIdx == -1){
            throw new IllegalArgumentException(ONLY_IMAGE_FILES_ARE_ACCEPTED_FOR_LOGOS);
        }
        String extention = path.substring(dotIdx).toLowerCase();
        return extention;
    }

    public String getPath() {
        return image;
    }

    public static Logo of(String path){
        return new Logo(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logo logo = (Logo) o;
        return Objects.equals(image, logo.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }
}
