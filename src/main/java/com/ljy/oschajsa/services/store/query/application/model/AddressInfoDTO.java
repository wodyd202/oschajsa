package com.ljy.oschajsa.services.store.query.application.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDTO {
    @NotBlank(message = "시를 입력해주세요.")
    private String city;

    @NotBlank(message = "도를 입력해주세요.")
    private String province;

    @NotBlank(message = "동을 입력해주세요.")
    private String dong;

    @Min(value = 0, message = "페이지는 0 이상 입력해주세요.")
    private int page;
}
