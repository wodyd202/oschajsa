package com.ljy.oschajsa.services.user.query.application.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    private String businessNumber;
    private String businessName;
    private StoreState state;

    @Builder
    public Store(String businessNumber, String businessName, StoreState state, LocalDate createDate) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.state = state;
    }
}
