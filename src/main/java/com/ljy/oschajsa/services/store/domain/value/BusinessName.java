package com.ljy.oschajsa.services.store.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessName {
    private String businessName;

    /**
     * @param businessName 상호명
     * - 상호명은 빈값을 허용하지 않음
     * - 한글, 숫자, 영어[대,소문자] 1자 이상 20자 이하만 허용
     */
    private BusinessName(String businessName) {
        verifyNotEmptyBusinessName(businessName);
        businessNameValidation(businessName);
        this.businessName = businessName;
    }

    private final static String BUSINESS_NAME_MUST_NOT_BE_EMTPY = "업체명을 입력해주세요.";
    private void verifyNotEmptyBusinessName(String businessName) {
        if(businessName.isEmpty()){
            throw new IllegalArgumentException(BUSINESS_NAME_MUST_NOT_BE_EMTPY);
        }
    }

    private final static Pattern BUSINESS_NAME_REGEX = Pattern.compile("^[가-힣a-zA-Z]{1,20}$");
    private final static String BUSINESS_NAME_CAN_USE_HANGUL_NUMBER_ALPHABETS_AND_THE_LENGTH_MUST_BE_BETWEEN_1_AND_20_CHARACTERS = "업체명은 [한글, 영어(대소문자)] 조합으로 1자 이상 20자 이하로 입력해주세요.";
    private void businessNameValidation(String businessName) {
        if(!BUSINESS_NAME_REGEX.matcher(businessName).matches()){
            throw new IllegalArgumentException(BUSINESS_NAME_CAN_USE_HANGUL_NUMBER_ALPHABETS_AND_THE_LENGTH_MUST_BE_BETWEEN_1_AND_20_CHARACTERS);
        }
    }

    public static BusinessName of(String businessName){
        return new BusinessName(businessName);
    }

    public String get() {
        return businessName;
    }

    @Override
    public String toString() {
        return "BusinessName{" +
                "businessName='" + businessName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessName that = (BusinessName) o;
        return Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessName);
    }
}
