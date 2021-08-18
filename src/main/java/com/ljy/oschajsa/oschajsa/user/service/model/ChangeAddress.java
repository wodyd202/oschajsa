package com.ljy.oschajsa.oschajsa.user.service.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeAddress {
    private Double lettitude, longtitude;

    @Builder
    public ChangeAddress(Double lettitude, Double longtitude) {
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
