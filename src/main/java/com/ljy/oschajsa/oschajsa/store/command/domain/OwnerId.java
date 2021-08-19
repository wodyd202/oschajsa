package com.ljy.oschajsa.oschajsa.store.command.domain;

import java.util.Objects;

public class OwnerId {
    private final String id;

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
