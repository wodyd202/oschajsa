package com.ljy.oschajsa.services.interest.domain.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registrant {
    private String id;

    private Registrant(String id) {
        this.id = id;
    }

    public static Registrant of(String id){
        return new Registrant(id);
    }

    public String get() {
        return id;
    }

    @Override
    public String toString() {
        return "Registrant{" +
                "id='" + id + '\'' +
                '}';
    }
}
