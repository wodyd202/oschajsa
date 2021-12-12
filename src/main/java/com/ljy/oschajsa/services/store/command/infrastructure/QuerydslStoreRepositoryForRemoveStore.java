package com.ljy.oschajsa.services.store.command.infrastructure;

import com.ljy.oschajsa.services.store.command.application.schedule.StoreRepositoryForRemoveStore;
import com.ljy.oschajsa.services.store.domain.QStore;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.ljy.oschajsa.services.store.domain.QStore.store;

@Repository
@AllArgsConstructor
public class QuerydslStoreRepositoryForRemoveStore implements StoreRepositoryForRemoveStore {
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void removeClosedStore() {
        jpaQueryFactory.delete(store)
                .where(store.state.eq(StoreState.PREPARED_CLOSE).and(store.closingReservationDate.eq(LocalDate.now())))
                .execute();
    }
}
