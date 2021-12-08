package com.ljy.oschajsa.services.interest.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessInfoModel {
    private String businessNumber;
    private String businessName;

    @Builder
    public BusinessInfoModel(String businessNumber, String businessName) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
    }
}
