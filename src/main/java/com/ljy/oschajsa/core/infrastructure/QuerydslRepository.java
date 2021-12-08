package com.ljy.oschajsa.core.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @param <T> Entity type
 * - Querydsl 사용시 공통적으로 사용할 Repository
 */
@Transactional
public class QuerydslRepository<T> {
    @Autowired protected JPAQueryFactory jpaQueryFactory;
    @PersistenceContext protected EntityManager entityManager;

    public void save(T entity) {
        if(entityManager.contains(entity)){
            entityManager.merge(entity);
        }else{
            entityManager.persist(entity);
        }
    }
}
