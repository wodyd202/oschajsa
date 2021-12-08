package com.ljy.oschajsa.services.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ChangeBusinessName {
    @NotBlank(message = "business name must not be empty")
    private String businessName;
}
