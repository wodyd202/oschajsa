package com.ljy.oschajsa.services.user.query.application.external;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Interest {
    private String businessName;
    private String businessNumber;
    private LocalDateTime createDateTime;

    public Interest(String businessName, String businessNumber, LocalDateTime createDateTime) {
        this.businessName = businessName;
        this.businessNumber = businessNumber;
        this.createDateTime = createDateTime;
    }
}
