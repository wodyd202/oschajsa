package com.ljy.oschajsa.services.store.domain;

import java.util.Optional;

public interface StoreTagRepository {
    void save(Tag tag);
    Optional<Tag> findByName(String name);
}
