package com.ljy.oschajsa.services.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, BusinessNumber> {
}
