package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import com.ljy.oschajsa.oschajsa.user.query.service.QUserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class QuerydslQUserRepository implements QUserRepository {
    @Autowired private JPAQueryFactory jpaQueryFactory;
    @PersistenceContext private EntityManager entityManager;

    @Override
    public void save(QueryUser queryUser) {
        if(entityManager.contains(queryUser)){
            entityManager.merge(queryUser);
        }else{
            entityManager.persist(queryUser);
        }
    }
}
