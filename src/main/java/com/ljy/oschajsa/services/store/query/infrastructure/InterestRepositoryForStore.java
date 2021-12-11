package com.ljy.oschajsa.services.store.query.infrastructure;

import com.ljy.oschajsa.services.store.query.application.external.InterestRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.ljy.oschajsa.services.interest.domain.QInterest.interest;

@Repository
@Transactional(readOnly = true)
public class InterestRepositoryForStore implements InterestRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public long getIntestTotalCount(String businessNumber) {
        return jpaQueryFactory.selectFrom(interest)
                .where(interest.storeInfo().businessNumber.eq(businessNumber))
                .fetchCount();
    }
}
