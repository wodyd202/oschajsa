package com.ljy.oschajsa.services.user.command.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.HashSet;
import java.util.Set;

@Embeddable
public class InterestStores {
    @ElementCollection
    @CollectionTable(name="interest_store")
    private final Set<Store> list;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected InterestStores(){
        list = new HashSet<>();
    }

    public InterestStores(Set<Store> list) {
        this.list = list;
    }

    public Set<Store> get() {
        return list;
    }

    public void interest(Store store) {
        if(list.contains(store)){
            list.remove(store);
        }else{
            list.add(store);
        }
    }
}
