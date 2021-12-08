package com.ljy.oschajsa.services.store.command.infrastructure;

import com.ljy.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.services.store.domain.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.StoreRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.oschajsa.services.store.domain.QStore.store;

@Repository
public class QuerydslStoreRepository extends QuerydslRepository<Store> implements StoreRepository {

    @Override
    public Optional<Store> findByBusinessNumber(BusinessNumber businessNumber) {
        Store findStore = jpaQueryFactory.selectFrom(store)
                .where(store.businessNumber().eq(businessNumber)).fetchFirst();
        return Optional.ofNullable(findStore);
    }
}
