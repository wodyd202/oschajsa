package com.ljy.oschajsa.services.user.command.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Store {
    private final String businessNumber;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected Store(){businessNumber=null;}

    private Store(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public static Store of(String businessNumber){
        return new Store(businessNumber);
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(businessNumber, store.businessNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessNumber);
    }
}
