package com.ljy.oschajsa.oschajsa.user.command.infrastructure;

import com.ljy.oschajsa.oschajsa.user.command.domain.User;
import com.ljy.oschajsa.oschajsa.user.command.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.command.service.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.user.command.domain.QUser.user;

@Repository
@Transactional
public class QuerydslUserRepository implements UserRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(User user) {
        if(entityManager.contains(user)){
            entityManager.merge(user);
        }else{
            entityManager.persist(user);
        }
    }

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
