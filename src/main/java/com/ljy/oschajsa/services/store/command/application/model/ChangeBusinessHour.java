package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeBusinessHour {
    @NotNull(message = "주간 오픈 시각 및 종료 시각을 입력해주세요.")
    @Min(value = 0, message = "주간 오픈 시각 및 종료 시각은 0~24 사이로 입력해주세요.")
    @Max(value = 23, message = "주간 오픈 시각 및 종료 시각은 0~24 사이로 입력해주세요.")
    private Integer weekdayStart, weekdayEnd;

    @NotNull(message = "주말 오픈 시각 및 종료 시각을 입력해주세요.")
    @Min(value = 0, message = "주말 오픈 시각 및 종료 시각은 0~24 사이로 입력해주세요.")
    @Max(value = 23, message = "주말 오픈 시각 및 종료 시각은 0~24 사이로 입력해주세요.")
    private Integer weekendStart, weekendEnd;
}
