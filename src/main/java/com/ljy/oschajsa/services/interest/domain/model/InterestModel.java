package com.ljy.oschajsa.services.interest.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestModel {
    private BusinessInfoModel businessInfo;

    @Builder
    public InterestModel(BusinessInfoModel businessInfo) {
        this.businessInfo = businessInfo;
    }
}
