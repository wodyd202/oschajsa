package com.ljy.oschajsa.oschajsa.core.infrastructure;

import com.ljy.oschajsa.oschajsa.user.query.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.user.query.model.QQueryUser;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import com.ljy.oschajsa.oschajsa.user.query.service.QUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QuerydslQUserRepository extends QuerydslRepository<QueryUser> implements QUserRepository {

    @Override
    public Optional<QueryUser> findByUserId(String username) {
        QueryUser queryUser = jpaQueryFactory.selectFrom(QQueryUser.queryUser)
                .where(QQueryUser.queryUser.userId.eq(username))
                .fetchFirst();
        return Optional.ofNullable(queryUser);
    }

}
