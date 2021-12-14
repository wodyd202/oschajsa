package com.ljy.oschajsa.services.user.command.application.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeAddress {
    private Double lettitude;
    private Double longtitude;

    @Builder
    public ChangeAddress(Double lettitude, Double longtitude) {
        this.lettitude = lettitude;
        this.longtitude = longtitude;
    }
}
