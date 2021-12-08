package com.ljy.oschajsa.services.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
public class OpenStore {
    @NotBlank(message = "business name must not be empty")
    private String businessName;
    @NotBlank(message = "business number must not be empty")
    @Pattern(regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}", message = "please check your input, business number format must be ***-**-****")
    private String businessNumber;
    @NotBlank(message = "business tel must not be empty")
    @Pattern(regexp = "[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}", message = "please check your input, business tel format must be 000-000-0000,00-000-0000,000-0000-0000")
    private String businessTel;
    @NotNull(message = "tags must not be emtpy")
    @Size(min = 1, max = 3, message = "maximum of 3 tags is allowed in the list of tags.")
    private List<String> tags;
    @Valid
    @NotNull(message = "business hour must not be empty")
    private ChangeBusinessHour businessHour;
    @Valid
    @NotNull(message = "coodinate must not be empty")
    private ChangeCoordinate coordinate;
}
