package com.ljy.oschajsa.oschajsa.user.query.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User Query Model
 */
@Entity
@Table(name = "q_user")
@Getter
public class QueryUser {
    @Id
    private final String userId;
    private String password;
    private final String nickname;

    @Embedded
    private QueryAddress address;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected QueryUser(){userId=null; nickname=null;}

    @Builder
    public QueryUser(String userId, String password, String nickname, QueryAddress address) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
    }
}
