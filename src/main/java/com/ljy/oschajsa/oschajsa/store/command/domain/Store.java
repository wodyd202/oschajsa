package com.ljy.oschajsa.oschajsa.store.command.domain;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.InvalidAddressException;
import com.ljy.oschajsa.oschajsa.store.command.domain.exception.*;
import com.ljy.oschajsa.oschajsa.store.command.domain.infra.LogoConverter;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "stores")
@DynamicUpdate
public class Store {
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
    private final BusinessNumber businessNumber;
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
    private final OwnerId ownerId;

    @Column(nullable = false)
    private final LocalDate createDate;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Store(){
        businessNumber = null;
        createDate = null;
        ownerId = null;
    }

    @Builder
    public Store(BusinessName businessName,
                 BusinessNumber businessNumber,
                 BusinessTel businessTel,
                 BusinessHour businessHour,
                 Tags tags,
                 Address address,
                 OwnerId ownerId) {
        verifyNotNullBusinessName(businessName);
        verifyNotNullBusinessNumber(businessNumber);
        verifyNotNullBusinessTel(businessTel);
        verifyNotNullBusinessHour(businessHour);
        verifyNotNullAddress(address);
        verifyNotNullTags(tags);
        this.tags = tags;
        this.businessName = businessName;
        this.businessNumber = businessNumber;
        this.businessTel = businessTel;
        this.businessHour = businessHour;
        this.address = address;
        this.ownerId = ownerId;
        this.createDate = LocalDate.now();
    }

    private static final String BUSINESS_NAME_MUST_NOT_BE_EMPTY = "business name must not be empty";
    private void verifyNotNullBusinessName(BusinessName businessName) {
        if(Objects.isNull(businessName)){
            throw new InvalidBusinessNameException(BUSINESS_NAME_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_NUMBER_MUST_NOT_BE_EMPTY = "business number must not be empty";
    private void verifyNotNullBusinessNumber(BusinessNumber businessNumber) {
        if(Objects.isNull(businessNumber)){
            throw new InvalidBusinessNumberException(BUSINESS_NUMBER_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_TEL_MUST_NOT_BE_EMPTY = "business tel must not be empty";
    private void verifyNotNullBusinessTel(BusinessTel businessTel) {
        if(Objects.isNull(businessTel)){
            throw new InvalidBusinessTelException(BUSINESS_TEL_MUST_NOT_BE_EMPTY);
        }
    }

    private static final String BUSINESS_HOUR_MUST_NOT_BE_EMPTY = "business hour must not be empty";
    private void verifyNotNullBusinessHour(BusinessHour businessHour) {
        if(Objects.isNull(businessHour)){
            throw new InvalidBusinessHourException(BUSINESS_HOUR_MUST_NOT_BE_EMPTY);
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
            throw new InvalidTagException(TAGS_MUST_NOT_BE_EMPTY);
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
     * @param image
     * - 업체 로고 변경
     */
    public void changeLogo(MultipartFile image) {
        verifyNotEmptyImage(image);
        logo = Logo.of(image.getOriginalFilename());
    }

    private static final String IMAGE_MUST_NOT_BE_EMPTY = "image must not be empty";
    private void verifyNotEmptyImage(MultipartFile image) {
        if(Objects.isNull(image)){
            throw new InvalidLogoException(IMAGE_MUST_NOT_BE_EMPTY);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public BusinessName getBusinessName() {
        return businessName;
    }

    public BusinessNumber getBusinessNumber() {
        return businessNumber;
    }

    public BusinessTel getBusinessTel() {
        return businessTel;
    }

    public StoreState getState() {
        return state;
    }

    public BusinessHour getBusinessHour() {
        return businessHour;
    }

    public Address getAddress() {
        return address;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Tags getTags() {
        return tags;
    }

    public Logo getLogo() {
        return logo;
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
