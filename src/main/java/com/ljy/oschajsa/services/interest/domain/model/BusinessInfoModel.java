package com.ljy.oschajsa.services.interest.domain.model;

import com.ljy.oschajsa.services.interest.domain.value.BusinessHour;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessInfoModel {
    private String businessNumber;
    private String businessName;
    private String logo;
    private BusinessHourModel businessHour;
    private boolean isOpen;

    @Builder
    public BusinessInfoModel(String businessNumber,
                             String businessName,
                             String logo,
                             BusinessHour businessHour) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.logo = logo;
        this.businessHour = businessHour.toModel();
        this.isOpen = businessHour.isCurrentOpen();
    }
}
