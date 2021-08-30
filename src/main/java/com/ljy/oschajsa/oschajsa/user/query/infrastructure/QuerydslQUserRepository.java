package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import com.ljy.oschajsa.oschajsa.user.query.model.UserState;
import com.ljy.oschajsa.oschajsa.user.query.model.QUserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.user.query.model.QQueryUser.queryUser;
import static com.querydsl.core.types.dsl.Expressions.asSimple;

@Repository
public class QuerydslQUserRepository extends QuerydslRepository<QueryUser> implements QUserRepository {

    @Override
    public Optional<QueryUser> findByUserId(String username) {
        QueryUser findUser = jpaQueryFactory.selectFrom(queryUser)
                .where(eqUserId(username), eqState(UserState.ACTIVE))
                .fetchFirst();
        return Optional.ofNullable(findUser);
    }

    @Override
    public Optional<QueryAddress> findAddressByUserId(String userId) {
        QueryUser findUser = jpaQueryFactory.select(Projections.constructor(QueryUser.class, queryUser.address()))
                .from(queryUser)
                .where(eqUserId(userId), eqState(UserState.ACTIVE))
                .fetchFirst();
        return Optional.ofNullable(findUser.getAddress());
    }

    @Override
    public Optional<QueryUser> login(String username) {
        QueryUser findUser = jpaQueryFactory.select(Projections.constructor(QueryUser.class, asSimple(username),queryUser.password))
                .from(queryUser)
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
