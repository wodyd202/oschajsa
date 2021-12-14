package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeStore {
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,20}$", message = "업체명은 [한글, 영어(대소문자)] 조합으로 1자 이상 20자 이하로 입력해주세요.")
    private String businessName;

    @Pattern(regexp = "[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}", message = "업체 전화번호 형식은 [000-000-0000,00-000-0000,000-0000-0000] 형식만 허용합니다.")
    private String businessTel;

    private ChangeBusinessHour businessHour;
}
