package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.command.domain.QInterestStores;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import com.ljy.oschajsa.oschajsa.user.query.model.UserState;
import com.ljy.oschajsa.oschajsa.user.query.model.QUserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SELECT_FROM_WHERE = "SELECT `query_user_user_id`,`interest_stores`\r\nFROM `q_interest_store`\r\nWHERE `query_user_user_id` = ?";
    @Override
    public Set<String> findInterestStoresByUserId(String userId) {
        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_WHERE);
        List<String> interest_stores = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return String.valueOf(resultSet.getString("interest_stores"));
            }
        }, new Object[]{userId});
        return interest_stores.stream().collect(Collectors.toSet());
    }

    private BooleanExpression eqState(UserState state) {
        return queryUser.state.eq(state);
    }

    private BooleanExpression eqUserId(String username) {
        return queryUser.userId.eq(username);
    }

}
