package com.ljy.oschajsa.services.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
public class ChangeTel {
    @NotBlank(message = "business tel must not be empty")
    @Pattern(regexp = "[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}", message = "please check your input, business tel format must be 000-000-0000,00-000-0000,000-0000-0000")
    private String tel;
}
