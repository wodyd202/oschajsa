package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeTel {
    @NotBlank(message = "업체 전화번호를 입력해주세요.")
    @Pattern(regexp = "[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}", message = "업체 전화번호 형식은 [000-000-0000,00-000-0000,000-0000-0000] 형식만 허용합니다.")
    private String tel;
}
