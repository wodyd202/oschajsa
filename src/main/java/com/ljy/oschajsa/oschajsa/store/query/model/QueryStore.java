package com.ljy.oschajsa.oschajsa.store.query.model;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "q_store")
@DynamicUpdate
public class QueryStore {
    @Id
    private final String businessNumber;
    private String businessName;
    private String businessTel;

    @ElementCollection
    private List<String> tags;
    private StoreState state;

    @Embedded
    private QueryBusinessHour queryBusinessHour;

    @Embedded
    private QueryAddress queryAddress;
    private final String ownerId;
    private final LocalDate createDate;

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
                      QueryBusinessHour queryBusinessHour,
                      QueryAddress queryAddress,
                      String ownerId,
                      LocalDate createDate) {
        this.businessNumber = businessNumber;
        this.businessName = businessName;
        this.businessTel = businessTel;
        this.tags = tags;
        this.state = state;
        this.queryBusinessHour = queryBusinessHour;
        this.queryAddress = queryAddress;
        this.ownerId = ownerId;
        this.createDate = createDate;
    }
}
