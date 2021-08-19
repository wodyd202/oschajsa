package com.ljy.oschajsa.oschajsa.store.command.domain;

import com.ljy.oschajsa.oschajsa.store.command.domain.exception.InvalidBusinessNumberException;

import java.util.Objects;
import java.util.regex.Pattern;

public class BusinessNumber {
    private final String number;
    private BusinessNumber(String number) {
        verifyNotEmptyBusinessNumber(number);
        businessNumberValidation(number);
        this.number = number;
    }

    private static final String BUSINESS_NUMBER_MUST_NOT_BE_EMPTY = "business number must not be empty";
    private void verifyNotEmptyBusinessNumber(String number) {
        if(number.isEmpty()){
            throw new InvalidBusinessNumberException(BUSINESS_NUMBER_MUST_NOT_BE_EMPTY);
        }
    }

    private static Pattern BUSINESS_NUMBER_REGEX = Pattern.compile("[0-9]{3}-[0-9]{2}-[0-9]{4}");
    private static final String PLEASE_CHECK_YOUR_INPUT_BUSINESS_NUMBER_FORMAT_MUST_BE = "please check your input, business number format must be ***-**-****";
    private void businessNumberValidation(String number) {
        if(!BUSINESS_NUMBER_REGEX.matcher(number).matches()){
            throw new InvalidBusinessNumberException(PLEASE_CHECK_YOUR_INPUT_BUSINESS_NUMBER_FORMAT_MUST_BE);
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
