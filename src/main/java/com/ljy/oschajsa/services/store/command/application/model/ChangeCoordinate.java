package com.ljy.oschajsa.services.store.command.application.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCoordinate {
    @NotNull(message = "위도 및 경도 좌표값을 입력해주세요.")
    private Double lettitude, longtitude;
}
