package com.ljy.oschajsa.services.interest.domain.value;

import com.ljy.oschajsa.services.interest.domain.model.BusinessInfoModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessInfo {
    private String businessNumber;
    private String businessName;

    public BusinessInfo(String businessNumber, String businessName) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
    }

    public BusinessInfoModel toModel(){
        return BusinessInfoModel.builder()
                .businessNumber(businessNumber)
                .businessName(businessName)
                .build();
    }
}
