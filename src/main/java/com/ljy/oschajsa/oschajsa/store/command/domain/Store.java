package com.ljy.oschajsa.oschajsa.store.command.domain;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.InvalidAddressException;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

public class Store {
    private final BusinessNumber businessNumber;
    private BusinessName businessName;
    private BusinessTel businessTel;
    private Tags tags;
    private StoreState state;
    private BusinessHour businessHour;
    private Address address;
    private final OwnerId ownerId;

    private final LocalDate createDate;

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
    public void register(StoreRegisterValidator storeRegisterValidator) {
        storeRegisterValidator.validation(businessNumber, tags);
        state = StoreState.OPEN;
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

}
