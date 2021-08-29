package com.ljy.oschajsa.oschajsa.store.query.application;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchDTO {
    private String city, province, dong;

    private Double longtitude, lettitude;
    private Integer differenceCoordinate;
}
