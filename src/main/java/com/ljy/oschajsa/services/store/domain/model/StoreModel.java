package com.ljy.oschajsa.services.store.domain.model;

import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessTelEvent;
import com.ljy.oschajsa.services.store.domain.event.ClosedStoreEvent;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class StoreModel {
    private String businessNumber;
    private String businessName;
    private String tel;
    private List<String> tags;
    private StoreState state;
    private BusinessHourModel businessHour;
    private AddressModel address;
    private String owner;
    private LocalDate createDate;
    private String logo;

    @Builder
    public StoreModel(String businessNumber,
                      String businessName,
                      String tel,
                      List<String> tags,
                      StoreState state,
                      BusinessHourModel businessHour,
                      AddressModel address,
                      String owner,
                      LocalDate createDate,
                      String logo) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.tags = tags;
        this.tel = tel;
        this.state = state;
        this.businessHour = businessHour;
        this.address = address;
        this.owner = owner;
        this.createDate = createDate;
        this.logo = logo;
    }

    public void on(ChangedLogoEvent event) {
        this.logo = event.getLogo();
    }

    public void on(ClosedStoreEvent event) {
        this.state = StoreState.CLOSE;
    }

    public void on(ChangedBusinessNameEvent event) {
        this.businessName = event.getBusinessName();
    }

    public void on(ChangedBusinessTelEvent event) {
        this.tel = event.getBusinessTel();
    }

    @Override
    public String toString() {
        return "StoreModel{" +
                "businessNumber='" + businessNumber + '\'' +
                ", businessName='" + businessName + '\'' +
                ", tags=" + tags +
                ", state=" + state +
                ", businessHour=" + businessHour +
                ", address=" + address +
                ", owner='" + owner + '\'' +
                ", createDate=" + createDate +
                ", logo='" + logo + '\'' +
                '}';
    }
}
