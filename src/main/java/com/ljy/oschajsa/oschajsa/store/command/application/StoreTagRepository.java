package com.ljy.oschajsa.oschajsa.store.command.application;

import com.ljy.oschajsa.oschajsa.store.command.domain.Tag;

import java.util.Optional;

public interface StoreTagRepository {
    void save(Tag tag);
    Optional<Tag> findByName(String name);
}
