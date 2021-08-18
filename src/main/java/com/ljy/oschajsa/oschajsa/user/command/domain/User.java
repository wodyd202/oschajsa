package com.ljy.oschajsa.oschajsa.user.command.domain;

import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Entity
@Table(name = "users")
public class User {
    /**
     * userId 사용자 아이디
     * password 사용자 비밀번호
     * nickName 사용자 닉네임
     * address 사용자 주소(필수값 아님)
     * createDateTime 사용자 생성일
     * state 사용자 상태(생성, 휴먼계정, 탈퇴)
     */
    @EmbeddedId
    private final UserId userId;
    @Embedded
    private Password password;
    @Embedded
    private final NickName nickname;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "dong", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "province", column = @Column(length = 50, nullable = true)),
            @AttributeOverride(name = "lettitude", column = @Column(nullable = true)),
            @AttributeOverride(name = "longtitude", column = @Column(nullable = true))
    })
    private Address address;

    @Column(nullable = false)
    private final LocalDateTime createDateTime;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserState state;

    // JPA에서 embedded로 사용시 기본 생성자 필요
    protected User(){ createDateTime = null; nickname = null; userId = null; }

    @Builder
    public User(UserId userId, Password password, NickName nickName, Address address) {
        verifyNotNullUserId(userId);
        verifyNotNullPassword(password);
        verifyNotNullNickname(nickName);
        this.userId = userId;
        this.password = password;
        this.nickname = nickName;
        this.address = address;
        createDateTime = LocalDateTime.now();
        state = UserState.ACTIVE;
    }

    private final static String NULL_USER_ID_MESSAGE = "user id must not be null";
    private void verifyNotNullUserId(UserId userId) {
        if(isNull(userId)){
            throw new InvalidUserIdException(NULL_USER_ID_MESSAGE);
        }
    }

    private final static String NULL_USER_PASSWORD_MESSAGE = "password must not be null";
    private void verifyNotNullPassword(Password password) {
        if(isNull(password)){
            throw new InvalidPasswordException(NULL_USER_PASSWORD_MESSAGE);
        }
    }

    private final static String NULL_USER_NICKNAME_MESSAGE = "nickname must not be null";
    private void verifyNotNullNickname(NickName nickName) {
        if(isNull(nickName)){
            throw new InvalidNicknameException(NULL_USER_NICKNAME_MESSAGE);
        }
    }

    /**
     * @param withCoodinate 변경할 주소
     * - 이미 회원탈퇴한 계정이라면 주소 변경을 할 수 없음
     */
    private final static String ALREADY_WITHDRAWAL_USER = "already withdrawal user";
    public void changeAddress(Address changeAddress) {
        if(isWithdrawal()){
            throw new AlreadyWithdrawalUserException(ALREADY_WITHDRAWAL_USER);
        }
        address = changeAddress;
    }

    /**
     * @param originPassword 기존 비밀번호
     * - 기존 비밀번호를 입력해야 회원 탈퇴할 수 있음
     */
    public void withdrawal(PasswordEncoder passwordEncoder, String originPassword) {
        if(!passwordEncoder.matches(originPassword, password.get())){
            throw new InvalidPasswordException("not equal password");
        }
        state = UserState.WITHDRAWAL;
    }

    private boolean isWithdrawal() {
        return state.equals(UserState.WITHDRAWAL);
    }

    /**
     * @param passwordEncoder
     * - 비밀번호 암호화
     */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = password.encode(passwordEncoder);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public UserId getUserId() {
        return userId;
    }

    public Password getPassword() {
        return password;
    }

    public NickName getNickname() {
        return nickname;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public UserState getState() {
        return state;
    }

}
