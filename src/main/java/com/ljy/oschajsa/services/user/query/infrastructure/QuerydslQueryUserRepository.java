package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.services.user.domain.QUser;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.UserRepository;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ljy.oschajsa.services.user.domain.QUser.user;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@AllArgsConstructor
public class QuerydslQueryUserRepository implements UserRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserModel> findById(String userId) {
        UserModel userModel = jpaQueryFactory.select(userModel())
                .from(user)
                .where(user.userId().id.eq(userId))
                .fetchFirst();
        if(userModel == null){
            return Optional.empty();
        }
        return Optional.of(userModel);
    }

    private ConstructorExpression<UserModel> userModel() {
        return constructor(UserModel.class,
                        user.userId().id,
                        user.nickname().name,
                        user.password().pw,
                        user.address(),
                        user.createDateTime,
                        user.state
                    );
    }
}
