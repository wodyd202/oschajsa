package com.ljy.oschajsa.services.store.domain.model;

import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.store.domain.event.*;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private long intestTotalCount;

    public void addInterestTotalCount(long intestTotalCount) {
        this.intestTotalCount = intestTotalCount;
    }

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

    protected void on(ChangedLogoEvent event) {
        this.logo = event.getLogo();
    }

    protected void on(ClosedStoreEvent event) {
        this.state = StoreState.CLOSE;
    }

    protected void on(ChangedBusinessNameEvent event) {
        this.businessName = event.getBusinessName();
    }

    protected void on(ChangedBusinessTelEvent event) {
        this.tel = event.getBusinessTel();
    }

    protected void on(ChangedBusinessHourEvent event){
        this.businessHour = event.getBusinessHour();
    }

    protected void on(RemovedTagEvent event){ this.tags.remove(event.getTag()); }

    protected void on(AddedTagEvent event){ this.tags.add(event.getTag()); }

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
