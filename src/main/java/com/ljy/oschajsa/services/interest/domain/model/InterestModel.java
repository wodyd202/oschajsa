package com.ljy.oschajsa.services.interest.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestModel {
    private BusinessInfoModel businessInfo;

    @Builder
    public InterestModel(BusinessInfoModel businessInfo) {
        this.businessInfo = businessInfo;
    }
}
