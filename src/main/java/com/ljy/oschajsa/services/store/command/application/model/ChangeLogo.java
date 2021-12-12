package com.ljy.oschajsa.services.store.command.application.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeLogo {
    private MultipartFile file;

    public ChangeLogo(MultipartFile file) {
        this.file = file;
    }
}
