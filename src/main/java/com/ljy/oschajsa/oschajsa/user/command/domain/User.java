package com.ljy.oschajsa.oschajsa.user.command.domain;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.user.command.domain.exception.AlreadyWithdrawalUserException;
import com.ljy.oschajsa.oschajsa.user.command.domain.exception.InvalidNicknameException;
import com.ljy.oschajsa.oschajsa.user.command.domain.exception.InvalidPasswordException;
import com.ljy.oschajsa.oschajsa.user.command.domain.exception.InvalidUserIdException;
import lombok.Builder;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table(name = "users")
@DynamicUpdate
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

    @Embedded
    private InterestStores interestStores;

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
        interestStores = new InterestStores();
    }

    /**
     * @param registerUserValidator 회원가입시 사용되는 validator
     * - validator에서 이미 해당 사용자가 존재하는지 체크함
     */
    final public void register(RegisterUserValidator registerUserValidator) {
        registerUserValidator.validation(userId);
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
    final public void changeAddress(Address changeAddress) {
        if(isWithdrawal()){
            throw new AlreadyWithdrawalUserException(ALREADY_WITHDRAWAL_USER);
        }
        address = changeAddress;
    }

    /**
     * @param originPassword 기존 비밀번호
     * - 기존 비밀번호를 입력해야 회원 탈퇴할 수 있음
     */
    final public void withdrawal(PasswordEncoder passwordEncoder, String originPassword) {
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
    final public void encodePassword(PasswordEncoder passwordEncoder) {
        password = password.encode(passwordEncoder);
    }

    /**
     * @param store 관심 업체 사업자 번호
     * - 관심 업체 등록 및 제거
     */
    final public void interestStore(InterestStoreValidator addInterestStoreValidator, Store store) {
        addInterestStoreValidator.validation(store, userId);
        interestStores.interest(store);
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

    public Set<Store> getInterestStores() {
        return interestStores.get();
    }
}
