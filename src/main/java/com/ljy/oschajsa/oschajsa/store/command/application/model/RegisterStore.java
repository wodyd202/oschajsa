package com.ljy.oschajsa.oschajsa.store.command.application.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RegisterStore {
    private String businessName;
    private String businessNumber;
    private String businessTel;
    private List<String> tags;
    private ChangeBusinessHour businessHour;
    private ChangeCoordinate coordinate;
}
