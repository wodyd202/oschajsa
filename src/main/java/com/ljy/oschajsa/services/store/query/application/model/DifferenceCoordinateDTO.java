package com.ljy.oschajsa.services.store.query.application.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifferenceCoordinateDTO {
    @NotNull(message = "경도 좌표를 입력해주세요.")
    private Double longtitude;

    @NotNull(message = "위도 좌표를 입력해주세요.")
    private Double lettitude;

    @NotNull(message = "거리 차이는 1km 이상 5km 이하로 입력해주세요.")
    @Min(value = 1, message = "거리 차이는 1km 이상 5km 이하로 입력해주세요.")
    @Max(value = 5, message = "거리 차이는 1km 이상 5km 이하로 입력해주세요.")
    private Integer differenceCoordinate;

    @Min(value = 0, message = "페이지는 0 이상 입력해주세요.")
    private int page;
}
