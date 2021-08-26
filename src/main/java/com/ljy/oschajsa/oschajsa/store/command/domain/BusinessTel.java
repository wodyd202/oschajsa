package com.ljy.oschajsa.oschajsa.store.command.domain;

import com.ljy.oschajsa.oschajsa.store.command.domain.exception.InvalidBusinessTelException;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class BusinessTel {
    private final String tel;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected BusinessTel(){
        tel = null;
    }

    private BusinessTel(String tel) {
        verifyNotEmptyTel(tel);
        telValidation(tel);
        this.tel = tel;
    }

    private static final String BUSINESS_TEL_MUST_NOT_BE_EMTPY = "business tel must not be emtpy";
    private void verifyNotEmptyTel(String tel) {
        if(tel.isEmpty()){
            throw new InvalidBusinessTelException(BUSINESS_TEL_MUST_NOT_BE_EMTPY);
        }
    }

    private static final Pattern TEL_REGEX = Pattern.compile("[0-2]{2,3}-[\\d]{3,4}-[\\d]{4}");
    private static final String INVALID_BUSINESS_TEL_MESSAGE = "please check your input, business tel format must be 000-000-0000,00-000-0000,000-0000-0000";
    private void telValidation(String tel) {
        if(!TEL_REGEX.matcher(tel).matches()){
            throw new InvalidBusinessTelException(INVALID_BUSINESS_TEL_MESSAGE);
        }
    }

    public static BusinessTel of(String tel){
        return new BusinessTel(Objects.requireNonNull(tel));
    }

    public String get() {
        return tel;
    }

    @Override
    public String toString() {
        return "BusinessTel{" +
                "tel='" + tel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessTel that = (BusinessTel) o;
        return Objects.equals(tel, that.tel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tel);
    }
}
