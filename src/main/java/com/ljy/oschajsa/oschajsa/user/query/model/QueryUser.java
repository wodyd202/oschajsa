package com.ljy.oschajsa.oschajsa.user.query.model;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


/**
 * User Query Model
 */
@Entity
@Table(name = "q_user")
@DynamicUpdate
public class QueryUser {
    @Id
    private final String userId;
    private String password;
    private final String nickname;

    @Embedded
    private QueryAddress address;

    private UserState state;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected QueryUser(){userId=null; nickname=null;}

    public QueryUser(QueryAddress address) {
        this.userId = null; nickname = null;
        this.address = address;
    }

    @Builder
    public QueryUser(String userId, String password, String nickname, QueryAddress address) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        state = UserState.ACTIVE;
    }

    public void changeAddress(QueryAddress address) {
        this.address = address;
    }

    public void withdrawal() {
        state = UserState.WITHDRAWAL;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public QueryAddress getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryUser queryUser = (QueryUser) o;
        return Objects.equals(userId, queryUser.userId) && Objects.equals(password, queryUser.password) && Objects.equals(nickname, queryUser.nickname) && Objects.equals(address, queryUser.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, nickname, address);
    }
}
