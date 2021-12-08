package com.ljy.oschajsa.services.user.query.application.external;

import com.ljy.oschajsa.services.store.domain.value.StoreState;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

public class Store {
    private String businessNumber;
    private String businessName;
    private StoreState state;
    private LocalDate createDate;

    @Builder
    public Store(String businessNumber, String businessName, StoreState state, LocalDate createDate) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.state = state;
        this.createDate = createDate;
    }
}
