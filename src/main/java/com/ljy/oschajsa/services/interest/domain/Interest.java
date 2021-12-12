package com.ljy.oschajsa.services.interest.domain;

import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 관심 업체
 */
@Slf4j
@Entity
@Table(name = "interest_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    // 업체 정보
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "businessNumber", column = @Column(name = "business_number", length = 11)),
            @AttributeOverride(name = "businessName", column = @Column(name = "business_name", length = 20, nullable = false))
    })
    private StoreInfo storeInfo;

    // 관심업체 등록자
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id", length = 15, nullable = false))
    private Registrant registrant;

    // 관심업체 등록일
    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Builder
    public Interest(StoreInfo storeInfo, Registrant registrant) {
        this.storeInfo = storeInfo;
        this.registrant = registrant;
        this.createDateTime = LocalDateTime.now();
        log.info("new store insterest : {}", this);
    }

    public Long getSeq() {
        return seq;
    }

    public InterestModel toModel() {
        return InterestModel.builder()
                .businessInfo(storeInfo.toModel())
                .build();
    }

    @Override
    public String toString() {
        return "Interest{" +
                "seq=" + seq +
                ", storeInfo=" + storeInfo +
                ", registrant=" + registrant +
                ", createDateTime=" + createDateTime +
                '}';
    }
}
