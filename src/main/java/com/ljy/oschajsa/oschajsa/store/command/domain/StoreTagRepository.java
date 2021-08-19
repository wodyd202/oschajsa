package com.ljy.oschajsa.oschajsa.store.command.domain;

import java.util.Optional;

public interface StoreTagRepository {
    void save(Tag tag);
    Optional<Tag> findByName(String name);
}
