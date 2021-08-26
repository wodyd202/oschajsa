package com.ljy.oschajsa.oschajsa.store.command.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.store.command.domain.BusinessNumber;
import com.ljy.oschajsa.oschajsa.store.command.domain.Store;
import com.ljy.oschajsa.oschajsa.store.command.domain.StoreRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.store.command.domain.QStore.store;

@Repository
public class QuerydslStoreRepository extends QuerydslRepository<Store> implements StoreRepository {

    @Override
    public Optional<Store> findByBusinessNumber(BusinessNumber businessNumber) {
        Store findStore = jpaQueryFactory.selectFrom(store)
                .where(store.businessNumber().eq(businessNumber)).fetchFirst();
        return Optional.ofNullable(findStore);
    }
}
