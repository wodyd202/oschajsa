package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenStore {
    @NotBlank(message = "업체 이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,20}$", message = "업체명은 [한글, 영어(대소문자)] 조합으로 1자 이상 20자 이하로 입력해주세요.")
    private String businessName;

    @NotBlank(message = "업체 사업자 번호를 입력해주세요.")
    @Pattern(regexp = "[0-9]{3}-[0-9]{2}-[0-9]{4}", message = "사업자 번호 형식은 ***-**-**** 형식만 허용합니다.")
    private String businessNumber;

    @NotBlank(message = "업체 전화번호를 입력해주세요.")
    @Pattern(regexp = "[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}", message = "업체 전화번호 형식은 [000-000-0000,00-000-0000,000-0000-0000] 형식만 허용합니다.")
    private String businessTel;

    @NotNull(message = "태그 정보를 입력해주세요.")
    @Size(min = 1, max = 3, message = "태그 정보는 1개 이상 3개 이하로 입력해주세요.")
    private List<String> tags;

    @Valid
    @NotNull(message = "업체 운영 시간을 입력해주세요.")
    private ChangeBusinessHour businessHour;

    @Valid
    @NotNull(message = "업체 주소 좌표를 입력해주세요.")
    private ChangeCoordinate coordinate;
}
