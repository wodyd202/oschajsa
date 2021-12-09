package com.ljy.oschajsa.services.interest.domain;

import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import com.ljy.oschajsa.services.interest.domain.value.UserId;
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
    private StoreInfo storeInfo;
    private UserId userId;
    private LocalDateTime createDateTime;

    @Builder
    public Interest(StoreInfo storeInfo, UserId userId) {
        this.storeInfo = storeInfo;
        this.userId = userId;
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
