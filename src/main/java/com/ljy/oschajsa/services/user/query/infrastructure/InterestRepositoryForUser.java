package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.services.interest.domain.QInterest;
import com.ljy.oschajsa.services.user.query.application.external.Interest;
import com.ljy.oschajsa.services.user.query.application.external.InterestRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ljy.oschajsa.services.interest.domain.QInterest.interest;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@Transactional(readOnly = true)
public class InterestRepositoryForUser implements InterestRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Interest> getInterests(String userId) {
        return jpaQueryFactory.select(constructor(Interest.class,
                        interest.storeInfo().businessName,
                        interest.storeInfo().businessNumber,
                        interest.createDateTime
                    ))
                .from(interest)
                .where(interest.registrant().id.eq(userId))
                .fetch();
    }
}
