package com.ljy.oschajsa.services.store.query.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljy.oschajsa.core.object.QueryAddress;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "q_store")
@DynamicUpdate
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class QueryStore {
    @Id
    private final String businessNumber;
    private String businessName;
    private String businessTel;

    @ElementCollection
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    private StoreState state;

    @Embedded
    private QueryBusinessHour businessHour;

    @Embedded
    private QueryAddress address;
    private final String ownerId;
    private final LocalDate createDate;

    private String logo;

    protected QueryStore() {
        this.businessNumber = null;
        this.ownerId = null;
        this.createDate = null;
    }

    @Builder
    public QueryStore(String businessNumber,
                      String businessName,
                      String businessTel,
                      List<String> tags,
                      StoreState state,
                      QueryBusinessHour businessHour,
                      QueryAddress address,
                      String ownerId,
                      LocalDate createDate,
                      String logo) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessTel = businessTel;
        this.tags = tags;
        this.state = state;
        this.businessHour = businessHour;
        this.address = address;
        this.ownerId = ownerId;
        this.createDate = createDate;
        this.logo = logo;
    }

    public void changeLogo(String logo) {
        this.logo = logo;
    }
}
