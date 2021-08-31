package com.ljy.oschajsa.oschajsa.user.query.model;

import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


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

    @ElementCollection
    @CollectionTable(name = "q_interest_store")
    private Set<String> interestStores;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected QueryUser(){userId=null; nickname=null;}

    public QueryUser(QueryAddress address) {
        this(null,null);
        this.address = address;
    }

    public QueryUser(String userId, String password){
        this.userId = userId;
        this.password = password;
        nickname = null;
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

    public void setInterestStores(Set<String> interestStores) {
        this.interestStores = interestStores;
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
    public String toString() {
        return "QueryUser{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", address=" + address +
                ", state=" + state +
                '}';
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
