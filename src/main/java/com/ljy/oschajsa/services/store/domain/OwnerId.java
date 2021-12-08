package com.ljy.oschajsa.services.store.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OwnerId {
    private final String id;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected OwnerId(){
        id = null;
    }

    private OwnerId(String id) {
        this.id = id;
    }

    public static OwnerId of(String id){
        return new OwnerId(id);
    }

    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerId ownerId = (OwnerId) o;
        return Objects.equals(id, ownerId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
