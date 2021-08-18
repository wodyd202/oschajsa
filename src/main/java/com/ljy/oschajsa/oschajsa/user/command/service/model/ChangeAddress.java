package com.ljy.oschajsa.oschajsa.user.command.service.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeAddress {
    @NotNull(message = "lettitude must not be null")
    private Double lettitude;
    @NotNull(message = "longtitude must not be null")
    private Double longtitude;

    @Builder
    public ChangeAddress(Double lettitude, Double longtitude) {
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
