package com.ljy.oschajsa.oschajsa.user.domain;

import lombok.Builder;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class User {
    /**
     * userId 사용자 아이디
     * password 사용자 비밀번호
     * nickName 사용자 닉네임
     * address 사용자 주소(필수값 아님)
     * createDateTime 사용자 생성일
     * state 사용자 상태(생성, 휴먼계정, 탈퇴)
     */
    private final UserId userId;
    private Password password;
    private final NickName nickname;
    private Address address;

    private final LocalDateTime createDateTime;
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

    private boolean isWithdrawal() {
        return state.equals(UserState.WITHDRAWAL);
    }

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
