package com.ljy.oschajsa.oschajsa.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class ChangeCoordinate {
    @NotNull(message = "lettitude and longtitude must not be empty")
    private Double lettitude, longtitude;
}
