package com.ljy.oschajsa.services.interest.domain.value;

import com.ljy.oschajsa.services.interest.domain.model.BusinessInfoModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreInfo {
    private String businessNumber;
    private String businessName;
    private String logo;

    @Embedded
    private BusinessHour businessHour;

    @Builder
    public StoreInfo(String businessNumber, String businessName, String logo, BusinessHour businessHour) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.logo = logo;
        this.businessHour = businessHour;
    }

    public BusinessInfoModel toModel(){
        return BusinessInfoModel.builder()
                .businessNumber(businessNumber)
                .businessName(businessName)
                .logo(logo)
                .businessHour(businessHour)
                .build();
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "businessNumber='" + businessNumber + '\'' +
                ", businessName='" + businessName + '\'' +
                ", logo='" + logo + '\'' +
                ", businessHour=" + businessHour +
                '}';
    }
}
