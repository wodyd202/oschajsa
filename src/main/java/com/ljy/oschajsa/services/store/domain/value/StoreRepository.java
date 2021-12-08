package com.ljy.oschajsa.services.store.domain.value;

import com.ljy.oschajsa.services.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, BusinessNumber> {
}
