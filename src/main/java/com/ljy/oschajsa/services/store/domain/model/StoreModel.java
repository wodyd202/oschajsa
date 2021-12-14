package com.ljy.oschajsa.services.store.domain.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreModel {
    private String businessNumber;
    private String businessName;
    private String tel;
    private List<String> tags;
    private StoreState state;
    private BusinessHourModel businessHour;
    private AddressModel address;
    private String owner;
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
        this.logo = logo;
    }

    protected void on(ChangedLogoEvent event) {
        this.logo = event.getLogo();
    }

    protected void on(PreparedClosedStoreEvent event) {
        this.state = StoreState.PREPARED_CLOSE;
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

    protected void on(StopedStoreEvent event) { state = StoreState.STOP;}

    protected void on(ReOpenedStoreEvent event) { state = StoreState.OPEN; }

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
                ", logo='" + logo + '\'' +
                '}';
    }
}
