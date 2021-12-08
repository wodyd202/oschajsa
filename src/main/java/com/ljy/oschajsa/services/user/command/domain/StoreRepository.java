package com.ljy.oschajsa.services.user.command.domain;

import java.util.Optional;

public interface StoreRepository {
    Optional<Store> findByBusinessNumber(String businessNumber);
}
