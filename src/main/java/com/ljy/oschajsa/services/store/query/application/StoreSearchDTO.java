package com.ljy.oschajsa.services.store.query.application;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDTO {
    private String city, province, dong;

    private Double longtitude, lettitude;

    @Min(value = 1, message = "거리 차이는 1km 이상 5km 이하로 입력해주세요.")
    @Max(value = 5, message = "거리 차이는 1km 이상 5km 이하로 입력해주세요.")
    private Integer differenceCoordinate;

    @Min(value = 0, message = "페이지는 0 이상 입력해주세요.")
    private int page;
}
