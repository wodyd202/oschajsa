package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeBusinessName {
    @NotBlank(message = "업체 이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,20}$", message = "업체명은 [한글, 영어(대소문자)] 조합으로 1자 이상 20자 이하로 입력해주세요.")
    private String businessName;
}
