package com.ljy.oschajsa.services.user.domain;

import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.user.domain.event.ChangedUserAddressEvent;
import com.ljy.oschajsa.services.user.domain.event.RegisteredUserEvent;
import com.ljy.oschajsa.services.user.domain.event.WithdrawaledUserEvent;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.domain.value.NickName;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

/**
 * 사용자 도메인
 */
@Slf4j
@Entity
@Table(name = "users")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractAggregateRoot<User> {

    // 사용자 아이디
    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id", length = 15))
    private UserId userId;

    // 사용자 비밀번호
    @Embedded
    private Password password;

    // 사용자 닉네임
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "nickname", length = 10))
    private NickName nickname;

    // 사용자 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressInfo.city", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "addressInfo.dong", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "addressInfo.province", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "coordinate.lettitude", column = @Column(nullable = true)),
            @AttributeOverride(name = "coordinate.longtitude", column = @Column(nullable = true))
    })
    private Address address;

    // 사용자 등록일
    @Column(nullable = false)
    private LocalDateTime createDateTime;

    // 상태
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

        registerEvent(new RegisteredUserEvent(userId, password, nickname, address, createDateTime));
        log.info("new user : {}", this);
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

    private final static String NULL_USER_ID_MESSAGE = "사용자의 아이디를 입력해주세요.";
    private void verifyNotNullUserId(UserId userId) {
        if(isNull(userId)){
            throw new IllegalArgumentException(NULL_USER_ID_MESSAGE);
        }
    }

    private final static String NULL_USER_PASSWORD_MESSAGE = "사용자의 비밀번호를 입력해주세요.";
    private void verifyNotNullPassword(Password password) {
        if(isNull(password)){
            throw new IllegalArgumentException(NULL_USER_PASSWORD_MESSAGE);
        }
    }

    private final static String NULL_USER_NICKNAME_MESSAGE = "사용자의 닉네임을 입력해주세요.";
    private void verifyNotNullNickname(NickName nickName) {
        if(isNull(nickName)){
            throw new IllegalArgumentException(NULL_USER_NICKNAME_MESSAGE);
        }
    }

    /**
     * @param withCoodinate 변경할 주소
     * - 이미 회원탈퇴한 계정이라면 주소 변경을 할 수 없음
     */
    private final static String ALREADY_WITHDRAWAL_USER = "이미 회원탈퇴한 사용자입니다.";
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
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password=" + password +
                ", nickname=" + nickname +
                ", address=" + address +
                ", createDateTime=" + createDateTime +
                ", state=" + state +
                '}';
    }
}
