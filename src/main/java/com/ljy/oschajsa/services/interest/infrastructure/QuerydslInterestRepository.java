package com.ljy.oschajsa.services.interest.infrastructure;

import com.ljy.oschajsa.services.interest.application.InterestRepository;
import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.QInterest;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.ljy.oschajsa.services.interest.domain.QInterest.interest;

@Repository
public class QuerydslInterestRepository implements InterestRepository {
    @PersistenceContext private EntityManager entityManager;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public Optional<Interest> findByUserIdAndBusinessNumber(Registrant userId, String businessNumber) {
        Interest interest = jpaQueryFactory.selectFrom(QInterest.interest)
                .where(QInterest.interest.registrant().eq(userId).and(QInterest.interest.storeInfo().businessNumber.eq(businessNumber)))
                .fetchFirst();
        return Optional.ofNullable(interest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Interest> findByUserId(Registrant userId) {
        return jpaQueryFactory.selectFrom(interest)
                .where(interest.registrant().eq(userId))
                .fetch();
    }

    @Override
    @Transactional
    public void deleteById(Long seq) {
        jpaQueryFactory.delete(interest)
                .where(interest.seq.eq(seq))
                .execute();
    }

    @Override
    @Transactional
    public void save(Interest interest) {
        entityManager.persist(interest);
    }
}
