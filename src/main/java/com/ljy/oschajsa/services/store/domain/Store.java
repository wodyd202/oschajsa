package com.ljy.oschajsa.services.store.domain;

import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.core.object.InvalidAddressException;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import com.ljy.oschajsa.services.store.domain.event.OpenedStoreEvent;
import com.ljy.oschajsa.services.store.domain.infra.LogoConverter;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
@Entity
@Table(name = "stores")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends AbstractAggregateRoot<Store> {

    /**
     * businessNumber 사업자 번호
     * businessName 상호명
     * businessTel 전화번호
     * tags 업체 태그 최대 3개
     * state 업체 상태(영업중, 판매중단, 폐업)
     * businessHour 영업 시간
     * address 주소
     * ownerId 업체 사장 아이디
     * createDate 업체 등록일
     */
    @EmbeddedId
    private BusinessNumber businessNumber;
    @Embedded
    private BusinessName businessName;
    @Embedded
    private BusinessTel businessTel;
    @Embedded
    private Tags tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreState state;

    @Embedded
    private BusinessHour businessHour;

    @Convert(converter = LogoConverter.class)
    private Logo logo;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "dong", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "province", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "lettitude", column = @Column(nullable = true)),
            @AttributeOverride(name = "longtitude", column = @Column(nullable = true))
    })
    private Address address;

    @Embedded
    private OwnerId ownerId;

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
        setTags(tags);
        setBusinessName(businessName);
        setAddress(address);
        setBusinessNumber(businessNumber);
        setBusinessTel(businessTel);
        setBusinessHour(businessHour);

        this.ownerId = ownerId;
        this.createDate = LocalDate.now();

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
        this.tags = tags;
    }

    private void setBusinessName(BusinessName businessName) {
        verifyNotNullBusinessName(businessName);
        this.businessName = businessName;
    }

    private static final String BUSINESS_NAME_MUST_NOT_BE_EMPTY = "business name must not be empty";
    private void verifyNotNullBusinessName(BusinessName businessName) {
        if(Objects.isNull(businessName)){
            throw new IllegalArgumentException(BUSINESS_NAME_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_NUMBER_MUST_NOT_BE_EMPTY = "business number must not be empty";
    private void verifyNotNullBusinessNumber(BusinessNumber businessNumber) {
        if(Objects.isNull(businessNumber)){
            throw new IllegalArgumentException(BUSINESS_NUMBER_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_TEL_MUST_NOT_BE_EMPTY = "business tel must not be empty";
    private void verifyNotNullBusinessTel(BusinessTel businessTel) {
        if(Objects.isNull(businessTel)){
            throw new IllegalArgumentException(BUSINESS_TEL_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_HOUR_MUST_NOT_BE_EMPTY = "business hour must not be empty";
    private void verifyNotNullBusinessHour(BusinessHour businessHour) {
        if(Objects.isNull(businessHour)){
            throw new IllegalArgumentException(BUSINESS_HOUR_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String ADDRESS_MUST_NOT_BE_EMPTY = "address must not be empty";
    private void verifyNotNullAddress(Address address) {
        if(Objects.isNull(address)){
            throw new InvalidAddressException(ADDRESS_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String TAGS_MUST_NOT_BE_EMPTY = "tags must not be empty";
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
    }

    /**
     * @param changer
     * @param image
     */
    public void changeLogo(OwnerId changer, MultipartFile image) {
        if(!isMyStore(changer)){
            throw new IllegalStateException("자신의 업체가 아닙니다.");
        }
        setLogo(image);
        registerEvent(new ChangedLogoEvent(businessNumber, logo));
    }

    private boolean isMyStore(OwnerId changer) {
        return ownerId.equals(changer);
    }

    private void setLogo(MultipartFile image) {
        verifyNotEmptyImage(image);
        logo = Logo.of(image.getOriginalFilename());
    }

    private static final String IMAGE_MUST_NOT_BE_EMPTY = "image must not be empty";
    private void verifyNotEmptyImage(MultipartFile image) {
        if(Objects.isNull(image)){
            throw new IllegalArgumentException(IMAGE_MUST_NOT_BE_EMPTY);
        }
    }

    public StoreModel toModel() {
        return StoreModel.builder()
                .businessNumber(businessNumber.get())
                .businessName(businessName.get())
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(businessNumber, store.businessNumber) && Objects.equals(businessName, store.businessName) && Objects.equals(businessTel, store.businessTel) && Objects.equals(tags, store.tags) && state == store.state && Objects.equals(businessHour, store.businessHour) && Objects.equals(address, store.address) && Objects.equals(ownerId, store.ownerId) && Objects.equals(createDate, store.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessNumber, businessName, businessTel, tags, state, businessHour, address, ownerId, createDate);
    }
}
