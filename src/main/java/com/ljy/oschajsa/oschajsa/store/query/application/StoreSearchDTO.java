package com.ljy.oschajsa.oschajsa.store.query.application;

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

    @Min(value = 1, message = "difference coordinate value are only allowed 0 or more and 5 or less.")
    @Max(value = 5, message = "difference coordinate value are only allowed 0 or more and 5 or less.")
    private Integer differenceCoordinate;

    @Min(value = 0, message = "page only 0 and above are allowed")
    private int page;
}
