package com.ljy.oschajsa.services.user.query.model;

import com.ljy.oschajsa.core.object.AddressModel;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
public class QueryStore {
    private String businessNumber;
    private String businessName;
    private String businessTel;
    private LocalDate createDate;
    private String state;

    private AddressModel address;
    private QueryBusinessHour businessHour;

    @Builder
    public QueryStore(String businessNumber, String businessName, String businessTel, Date createDate, String state, AddressModel address, QueryBusinessHour businessHour) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessTel = businessTel;
        this.createDate = createDate.toLocalDate();
        this.state = state;
        this.address = address;
        this.businessHour = businessHour;
    }
}
