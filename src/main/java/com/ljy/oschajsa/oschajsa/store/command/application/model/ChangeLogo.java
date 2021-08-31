package com.ljy.oschajsa.oschajsa.store.command.application.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeLogo {
    @NotNull(message = "file must not be empty")
    private final MultipartFile file;

    public ChangeLogo(MultipartFile file) {
        this.file = file;
    }
}
