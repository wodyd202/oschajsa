package com.ljy.oschajsa.services.interest.domain.model;

import com.ljy.oschajsa.services.interest.domain.value.BusinessHour;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessInfoModel {
    private String businessNumber;
    private String businessName;
    private String logo;
    private BusinessHourModel businessHour;

    @Builder
    public BusinessInfoModel(String businessNumber,
                             String businessName,
                             String logo,
                             BusinessHour businessHour) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.logo = logo;
        this.businessHour = businessHour.toModel();
    }
}
