package com.ljy.oschajsa.services.store.domain;

import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.store.domain.event.*;
import com.ljy.oschajsa.services.store.domain.infra.LogoConverter;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 업체 도메인
 */
@Slf4j
@Entity
@Table(name = "stores", indexes = @Index(columnList = "province, city, dong"))
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends AbstractAggregateRoot<Store> {

    // 사업자 번호
    @EmbeddedId
    @AttributeOverride(name = "number", column = @Column(name = "business_number", length = 11))
    private BusinessNumber businessNumber;

    // 상호명
    @Embedded
    @AttributeOverride(name = "businessName", column = @Column(name = "business_name", length = 20, nullable = false))
    private BusinessName businessName;

    // 업체 전화번호
    @Embedded
    @AttributeOverride(name = "tel", column = @Column(name = "tel", length = 13, nullable = false))
    private BusinessTel businessTel;

    // 업체 태그 정보
    @Embedded
    private Tags tags;

    // 업체 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private StoreState state;

    // 업체 운영 시간
    @Embedded
    private BusinessHour businessHour;

    // 업체 로고
    @Convert(converter = LogoConverter.class)
    private Logo logo;

    // 업체 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressInfo.city", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "addressInfo.dong", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "addressInfo.province", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "coordinate.lettitude", column = @Column(nullable = true)),
            @AttributeOverride(name = "coordinate.longtitude", column = @Column(nullable = true))
    })
    private Address address;

    // 업체 사장
    @Embedded
    private OwnerId ownerId;

    // 업체 등록일
    @Column(nullable = false)
    private LocalDate createDate;

    @Builder
    public Store(BusinessName businessName,
                 BusinessNumber businessNumber,
                 BusinessTel businessTel,
                 BusinessHour businessHour,
                 Tags tags,
                 Address address,
                 OwnerId ownerId) {
        setBusinessName(businessName);
        setAddress(address);
        setBusinessNumber(businessNumber);
        setBusinessTel(businessTel);
        setBusinessHour(businessHour);
        setTags(tags);

        this.ownerId = ownerId;
        this.createDate = LocalDate.now();

        log.info("new store : {}", this);
        registerEvent(new OpenedStoreEvent(businessNumber, businessName, tags, businessTel, businessHour, address, ownerId, createDate));
    }

    private void setBusinessHour(BusinessHour businessHour) {
        verifyNotNullBusinessHour(businessHour);
        this.businessHour = businessHour;
    }

    private void setBusinessTel(BusinessTel businessTel) {
        verifyNotNullBusinessTel(businessTel);
        this.businessTel = businessTel;
    }

    private void setBusinessNumber(BusinessNumber businessNumber) {
        verifyNotNullBusinessNumber(businessNumber);
        this.businessNumber = businessNumber;
    }

    private void setAddress(Address address) {
        verifyNotNullAddress(address);
        this.address = address;
    }

    private void setTags(Tags tags) {
        verifyNotNullTags(tags);
        tags.setStore(this);
        this.tags = tags;
    }

    private void setBusinessName(BusinessName businessName) {
        verifyNotNullBusinessName(businessName);
        this.businessName = businessName;
    }

    private static final String BUSINESS_NAME_MUST_NOT_BE_EMPTY = "업체명을 입력해주세요.";
    private void verifyNotNullBusinessName(BusinessName businessName) {
        if(Objects.isNull(businessName)){
            throw new IllegalArgumentException(BUSINESS_NAME_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_NUMBER_MUST_NOT_BE_EMPTY = "업체 사업자번호를 입력해주세요.";
    private void verifyNotNullBusinessNumber(BusinessNumber businessNumber) {
        if(Objects.isNull(businessNumber)){
            throw new IllegalArgumentException(BUSINESS_NUMBER_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_TEL_MUST_NOT_BE_EMPTY = "업체 전화번호를 입력해주세요.";
    private void verifyNotNullBusinessTel(BusinessTel businessTel) {
        if(Objects.isNull(businessTel)){
            throw new IllegalArgumentException(BUSINESS_TEL_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_HOUR_MUST_NOT_BE_EMPTY = "업체 운영 시간을 입력해주세요.";
    private void verifyNotNullBusinessHour(BusinessHour businessHour) {
        if(Objects.isNull(businessHour)){
            throw new IllegalArgumentException(BUSINESS_HOUR_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String ADDRESS_MUST_NOT_BE_EMPTY = "업체 주소를 입력해주세요.";
    private void verifyNotNullAddress(Address address) {
        if(Objects.isNull(address)){
            throw new IllegalArgumentException(ADDRESS_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String TAGS_MUST_NOT_BE_EMPTY = "업체 태그를 입력해주세요.";
    private void verifyNotNullTags(Tags tags) {
        if(Objects.isNull(tags)){
            throw new IllegalArgumentException(TAGS_MUST_NOT_BE_EMPTY);
        }
    }

    /**
     * @param storeRegisterValidator
     * - 업체 등록전 등록이 가능한지 체크하는 validator
     * - 유효성 검사 후 state 변경
     */
    public void open(StoreOpenValidator storeRegisterValidator) {
        storeRegisterValidator.validation(businessNumber, tags);
        state = StoreState.OPEN;
        log.info("store open validation success : {}", businessNumber);
    }

    /**
     * @param changer
     * @param image
     */
    public void changeLogo(OwnerId changer, MultipartFile image) {
        verifyIsMyStore(changer);
        Logo originLogo = this.logo;
        setLogo(image);
        registerEvent(new ChangedLogoEvent(businessNumber, logo));
        log.info("{} store change logo : {} to {}", businessNumber, originLogo, this.logo);
    }

    private boolean isMyStore(OwnerId changer) {
        return ownerId.equals(changer);
    }

    private void setLogo(MultipartFile image) {
        logo = image != null ? Logo.of(image.getOriginalFilename()) : null;
    }

    /**
     * @param closer
     * # 업체 폐업
     */
    public void close(OwnerId closer) {
        verifyIsMyStore(closer);
        this.state = StoreState.CLOSE;
        registerEvent(new ClosedStoreEvent(businessNumber));
        log.info("{} store close", businessNumber);
    }

    /**
     * @param businessName
     * @param changer
     * # 업체명 변경
     */
    public void changeBusinessName(BusinessName businessName, OwnerId changer) {
        verifyIsMyStore(changer);
        BusinessName originBusinessName = this.businessName;
        setBusinessName(businessName);
        registerEvent(new ChangedBusinessNameEvent(businessNumber, businessName));
        log.info("{} store change name : {} to {}", businessNumber, originBusinessName, this.businessName);
    }

    /**
     * @param businessTel
     * @param changer
     * # 업체 전화번호 변경
     */
    public void changeTel(BusinessTel businessTel, OwnerId changer) {
        verifyIsMyStore(changer);
        BusinessTel originBusinessTel = this.businessTel;
        setBusinessTel(businessTel);
        registerEvent(new ChangedBusinessTelEvent(businessNumber, businessTel));
        log.info("{} store change tel : {} to {}", businessNumber, originBusinessTel, this.businessTel);
    }

    /**
     * @param businessHour
     * @param changer
     * # 업체 운영시간 변경
     */
    public void changeBusinessHour(BusinessHour businessHour, OwnerId changer) {
        verifyIsMyStore(changer);
        BusinessHour originBusinessHour = this.businessHour;
        setBusinessHour(businessHour);
        registerEvent(new ChangedBusinessHourEvent(businessNumber, businessHour));
        log.info("{} store change business hour : {} to {}", businessNumber, originBusinessHour, this.businessHour);
    }

    /**
     * @param tag
     * @param remover
     * # 업체 태그 제거
     */
    public void removeTag(Tag tag, OwnerId remover) {
        verifyIsMyStore(remover);
        tags.remove(tag);
        registerEvent(new RemovedTagEvent(businessNumber, tag));
        log.info("{} store remove tag : {} to {}", businessNumber, tag);
    }

    /**
     * @param tag
     * @param adder
     * # 업체 태그 추가
     */
    public void addTag(Tag tag, OwnerId adder) {
        verifyIsMyStore(adder);
        tags.add(tag, this);
        registerEvent(new AddedTagEvent(businessNumber, tag));
        log.info("{} store add tag : {} to {}", businessNumber, tag);
    }

    private void verifyIsMyStore(OwnerId ownerId) {
        if(!isMyStore(ownerId)){
            throw new IllegalStateException("자신의 업체가 아닙니다.");
        }
    }

    public boolean hasLogo() {
        return logo != null && logo.getPath() != null;
    }

    public String getLogo() {
        return logo.getPath();
    }

    public StoreModel toModel() {
        return StoreModel.builder()
                .businessNumber(businessNumber.get())
                .businessName(businessName.get())
                .tel(businessTel.get())
                .tags(tags.get().stream().map(Tag::get).collect(Collectors.toList()))
                .state(state)
                .businessHour(businessHour.toModel())
                .address(address.toModel())
                .owner(ownerId.get())
                .createDate(createDate)
                .logo(logo == null ? null : logo.getPath())
                .build();
    }

    @Override
    public String toString() {
        return "Store{" +
                "businessNumber=" + businessNumber +
                ", businessName=" + businessName +
                ", businessTel=" + businessTel +
                ", tags=" + tags +
                ", state=" + state +
                ", businessHour=" + businessHour +
                ", logo=" + logo +
                ", address=" + address +
                ", ownerId=" + ownerId +
                ", createDate=" + createDate +
                '}';
    }
}
