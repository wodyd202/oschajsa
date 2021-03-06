package com.ljy.oschajsa.services.store.domain.value;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class BusinessNumber implements Serializable {
    private final String number;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected BusinessNumber(){number=null;}

    private BusinessNumber(String number) {
        verifyNotEmptyBusinessNumber(number);
        businessNumberValidation(number);
        this.number = number;
    }

    private static final String BUSINESS_NUMBER_MUST_NOT_BE_EMPTY = "사업자 번호를 입력해주세요.";
    private void verifyNotEmptyBusinessNumber(String number) {
        if(number.isEmpty()){
            throw new IllegalArgumentException(BUSINESS_NUMBER_MUST_NOT_BE_EMPTY);
        }
    }

    private static Pattern BUSINESS_NUMBER_REGEX = Pattern.compile("[0-9]{3}-[0-9]{2}-[0-9]{4}");
    private static final String PLEASE_CHECK_YOUR_INPUT_BUSINESS_NUMBER_FORMAT_MUST_BE = "사업자 번호 형식은 ***-**-**** 형식만 허용합니다.";
    private void businessNumberValidation(String number) {
        if(!BUSINESS_NUMBER_REGEX.matcher(number).matches()){
            throw new IllegalArgumentException(PLEASE_CHECK_YOUR_INPUT_BUSINESS_NUMBER_FORMAT_MUST_BE);
        }
    }

    public static BusinessNumber of(String number){
        return new BusinessNumber(Objects.requireNonNull(number));
    }

    public String get() {
        return number;
    }

    @Override
    public String toString() {
        return "BusinessNumber{" +
                "number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessNumber that = (BusinessNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
