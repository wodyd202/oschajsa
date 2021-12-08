package com.ljy.oschajsa.services.interest.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId {
    private String id;

    private UserId(String id) {
        this.id = id;
    }

    public static UserId of(String id){
        return new UserId(id);
    }

    public String get() {
        return id;
    }
}
