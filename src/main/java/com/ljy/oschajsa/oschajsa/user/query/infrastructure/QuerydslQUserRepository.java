package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.user.query.model.QQueryUser;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import com.ljy.oschajsa.oschajsa.user.query.model.UserState;
import com.ljy.oschajsa.oschajsa.user.query.service.QUserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.user.query.model.QQueryUser.queryUser;

@Repository
public class QuerydslQUserRepository extends QuerydslRepository<QueryUser> implements QUserRepository {

    @Override
    public Optional<QueryUser> findByUserId(String username) {
        QueryUser findUser = jpaQueryFactory.selectFrom(queryUser)
                .where(eqUserId(username), eqState(UserState.ACTIVE))
                .fetchFirst();
        return Optional.ofNullable(findUser);
    }

    private BooleanExpression eqState(UserState state) {
        return queryUser.state.eq(state);
    }

    private BooleanExpression eqUserId(String username) {
        return queryUser.userId.eq(username);
    }

}
