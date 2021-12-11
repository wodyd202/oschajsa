package com.ljy.oschajsa.services.interest.domain;

import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 관심 업체
 */
@Entity
@Table(name = "interest_store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    // 업체 정보
    private StoreInfo storeInfo;

    // 관심업체 등록자
    private Registrant registrant;

    // 관심업체 등록일
    private LocalDateTime createDateTime;

    @Builder
    public Interest(StoreInfo storeInfo, Registrant registrant) {
        this.storeInfo = storeInfo;
        this.registrant = registrant;
        this.createDateTime = LocalDateTime.now();
    }

    public Long getSeq() {
        return seq;
    }

    public InterestModel toModel() {
        return InterestModel.builder()
                .businessInfo(storeInfo.toModel())
                .build();
    }
}
