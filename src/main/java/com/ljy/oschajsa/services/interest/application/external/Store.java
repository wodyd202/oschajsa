package com.ljy.oschajsa.services.interest.application.external;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    private String businessNumber;
    private String businessName;

    public Store(String businessNumber, String businessName) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
    }
}
