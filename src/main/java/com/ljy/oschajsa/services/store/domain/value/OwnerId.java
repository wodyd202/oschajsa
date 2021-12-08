package com.ljy.oschajsa.services.store.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerId {
    private String id;

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
