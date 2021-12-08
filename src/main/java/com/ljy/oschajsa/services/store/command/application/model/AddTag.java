package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddTag {
    @NotBlank(message = "태그 정보를 입력해주세요.")
    private String tag;
}
