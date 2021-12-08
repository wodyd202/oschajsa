package com.ljy.oschajsa.services.user.domain.value;

import com.ljy.oschajsa.core.object.Address;
import com.ljy.oschajsa.services.user.domain.UserState;
import com.ljy.oschajsa.services.user.domain.event.ChangedUserAddressEvent;
import com.ljy.oschajsa.services.user.domain.event.RegisteredUserEvent;
import com.ljy.oschajsa.services.user.domain.event.WithdrawaledUserEvent;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

/**
 * 사용자 도메인
 */
@Entity
@Table(name = "users")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractAggregateRoot<User> {

    /**
     * userId 사용자 아이디
     * password 사용자 비밀번호
     * nickName 사용자 닉네임
     * address 사용자 주소(필수값 아님)
     * createDateTime 사용자 생성일
     * state 사용자 상태(생성, 휴먼계정, 탈퇴)
     */
    @EmbeddedId
    private UserId userId;
    @Embedded
    private Password password;
    @Embedded
    private NickName nickname;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "dong", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "province", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "lettitude", column = @Column(nullable = true)),
            @AttributeOverride(name = "longtitude", column = @Column(nullable = true))
    })
    private Address address;

    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserState state;

    @Builder
    public User(UserId userId, Password password, NickName nickName, Address address) {
        setUserId(userId);
        setPassword(password);
        setNickname(nickName);
        setAddress(address);
        createDateTime = LocalDateTime.now();
        state = UserState.ACTIVE;

        //
        registerEvent(new RegisteredUserEvent(userId, password, nickname, address, createDateTime));
    }

    private void setUserId(UserId userId) {
        verifyNotNullUserId(userId);
        this.userId = userId;
    }

    private void setPassword(Password password) {
        verifyNotNullPassword(password);
        this.password = password;
    }

    private void setNickname(NickName nickName) {
        verifyNotNullNickname(nickName);
        this.nickname = nickName;
    }

    private void setAddress(Address address) {
        this.address = address;
    }

    private final static String NULL_USER_ID_MESSAGE = "user id must not be null";
    private void verifyNotNullUserId(UserId userId) {
        if(isNull(userId)){
            throw new IllegalArgumentException(NULL_USER_ID_MESSAGE);
        }
    }

    private final static String NULL_USER_PASSWORD_MESSAGE = "password must not be null";
    private void verifyNotNullPassword(Password password) {
        if(isNull(password)){
            throw new IllegalArgumentException(NULL_USER_PASSWORD_MESSAGE);
        }
    }

    private final static String NULL_USER_NICKNAME_MESSAGE = "nickname must not be null";
    private void verifyNotNullNickname(NickName nickName) {
        if(isNull(nickName)){
            throw new IllegalArgumentException(NULL_USER_NICKNAME_MESSAGE);
        }
    }

    /**
     * @param withCoodinate 변경할 주소
     * - 이미 회원탈퇴한 계정이라면 주소 변경을 할 수 없음
     */
    private final static String ALREADY_WITHDRAWAL_USER = "already withdrawal user";
    final public void changeAddress(Address changeAddress) {
        if(isWithdrawal()){
            throw new IllegalStateException(ALREADY_WITHDRAWAL_USER);
        }
        setAddress(changeAddress);
        registerEvent(new ChangedUserAddressEvent(userId, address));
    }

    /**
     * @param originPassword 기존 비밀번호
     * - 기존 비밀번호를 입력해야 회원 탈퇴할 수 있음
     */
    final public void withdrawal(PasswordEncoder passwordEncoder, String originPassword) {
        if(!passwordEncoder.matches(originPassword, password.get())){
            throw new IllegalArgumentException("not equal password");
        }
        state = UserState.WITHDRAWAL;
        registerEvent(new WithdrawaledUserEvent(userId));
    }

    private boolean isWithdrawal() {
        return state.equals(UserState.WITHDRAWAL);
    }

    public UserModel toModel() {
        return UserModel.builder()
                .userId(userId.get())
                .nickname(nickname.get())
                .address(address == null ? null : address.toModel())
                .createDateTime(createDateTime)
                .state(state)
                .build();
    }
}
