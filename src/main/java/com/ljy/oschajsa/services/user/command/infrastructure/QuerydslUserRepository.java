package com.ljy.oschajsa.services.user.command.infrastructure;

import com.ljy.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.services.user.command.domain.User;
import com.ljy.oschajsa.services.user.command.domain.UserId;
import com.ljy.oschajsa.services.user.command.domain.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.user.command.domain.QUser.user;

@Repository
@Transactional
public class QuerydslUserRepository extends QuerydslRepository<User> implements UserRepository {

    @Override
    public Optional<User> findByUserId(UserId userId) {
        User findUser = jpaQueryFactory.selectFrom(user)
                .where(eqUserId(userId))
                .fetchFirst();
        return Optional.ofNullable(findUser);
    }

    private BooleanExpression eqUserId(UserId userId) {
        return user.userId().eq(userId);
    }
}
