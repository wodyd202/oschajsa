package com.ljy.oschajsa.services.user.domain;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.common.address.model.Address;
import com.ljy.oschajsa.services.common.address.model.AddressInfo;
import com.ljy.oschajsa.services.common.address.model.Coordinate;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import com.ljy.oschajsa.services.user.command.application.*;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.domain.value.NickName;
import com.ljy.oschajsa.services.user.domain.value.Password;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.ljy.oschajsa.services.user.UserFixture.registeredUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {

    @DisplayName("사용자 아이디는 null값을 허용하지 않음")
    @Test
    void nullUserId(){
        // when
        assertThrows(NullPointerException.class, ()->{
            UserId.of(null);
        });
    }

    @DisplayName("사용자 아이디는 빈값을 허용하지 않음")
    void emptyUserId(){
        // when
        assertThrows(IllegalArgumentException.class,()->{
           UserId.of("");
        });
    }

    @DisplayName("사용자 아이디는 영어[소문자], 숫자만 허용하고 4자 이상 15자 이하로 입력해야하며, 첫 글자는 반드시 영문이여야한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", " test3", " 3test", "test 3","test3 "})
    void invalidUserId(String id){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
           UserId.of(id);
        });
    }

    @Test
    @DisplayName("사용자 아이디 정상 입력")
    void validUserId(){
        // when
        UserId userId = UserId.of("test3");

        // then
        assertEquals(userId, UserId.of("test3"));
        assertEquals(userId.get(), "test3");
    }

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @DisplayName("사용자 비밀번호는 null값을 허용하지 않음")
    @Test
    void nullPassword(){
        // when
         assertThrows(IllegalArgumentException.class,()->{
            Password.of(null, passwordEncoder);
         });
    }

    @DisplayName("사용자 비밀번호는 빈값을 허용하지 않음")
    @Test
    void emptyPassword(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
           Password.of("", passwordEncoder);
        });
    }

    @DisplayName("사용자 비밀번호는 공백을 제외한 숫자, 영문[대,소문자], 특수문자(._%+=)만 허용하며, 8자 이상 15자 이하만 허용")
    @ParameterizedTest
    @ValueSource(strings = {"테스트"," password","password ","pass word"})
    void invalidPassword(String pw){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            Password.of(pw, passwordEncoder);
        });
    }

    @Test
    @DisplayName("사용자 비밀번호 정상 입력")
    void validPassword(){
        // when
        Password password = Password.of("password", passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("password", password.get()));
    }

    @Test
    @DisplayName("사용자 닉네임은 null을 허용하지 않음")
    void nullNickName(){
        // when
        assertThrows(NullPointerException.class,()->{
           NickName.of(null);
        });
    }

    @Test
    @DisplayName("사용자 닉네임은 빈값을 허용하지 않음")
    void emptyNickname(){
        // when
        assertThrows(IllegalArgumentException.class,()->{
            NickName.of("");
        });
    }

    @DisplayName("사용자 닉네임은 한글, 영어[대,소문자], 숫자만 허용하고 3자 이상 10자 이하여야함")
    @ParameterizedTest
    @ValueSource(strings = {"ㅌㅅㅌ","테스트 "," 테스트","테스트%","테스트@","TEST "})
    void invalidNickName(String nickname){
        // when
        assertThrows(IllegalArgumentException.class,()->{
           NickName.of(nickname);
        });
    }

    @DisplayName("사용자 닉네임 정상 입력")
    @Test
    void validNickname(){
        // when
        NickName nickname = NickName.of("test");

        // then
        assertEquals(nickname, NickName.of("test"));
        assertEquals(nickname.get(), "test");
    }

    @Test
    @DisplayName("사용자 생성시 주소 좌표값을 입력하지 않을 경우 Address가 null값이됨")
    void emptyCoordinate(){
        // give
        // 사용자 생성시 주소는 입력하지 않아도 됨
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .password("password")
                .nickname("nickname")
                .build();
        AddressHelper addressHelper = mock(AddressHelper.class);

        // when
        UserMapper userMapper = new UserMapper(addressHelper, passwordEncoder);
        User user = userMapper.mapFrom(registerUser);
        UserModel userModel = user.toModel();

        // then
        assertEquals(userModel.getUserId(), UserId.of("userid").get());
        assertEquals(userModel.getNickname(), NickName.of("nickname").get());
        assertNull(userModel.getAddress());
    }

    @Test
    void containCoordinate(){
        // given
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .password("password")
                .nickname("nickname")
                // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
                .lettitude(1.0)
                .longtitude(1.0)
                .build();

        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(Coordinate.withLattitudeLongtitude(1.0,1.0)))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        // when
        UserMapper userMapper = new UserMapper(addressHelper, passwordEncoder);
        User user = userMapper.mapFrom(registerUser);
        UserModel userModel = user.toModel();

        // then
        assertNotNull(userModel.getAddress());
        Assertions.assertEquals(userModel.getAddress().getCity(),"서울특별시");
    }

    @Test
    @DisplayName("이미 존재하는 아이디")
    void alreadyExistUserId(){
        // when
        UserRepository userRepository = mock(UserRepository.class);

        when(userRepository.findById(UserId.of("userid")))
                .thenReturn(Optional.of(mock(User.class)));

        // when
        assertTrue(userRepository.findById(UserId.of("userid")).isPresent());
    }

    @Test
    @DisplayName("사용자 주소지 변경")
    void changeAddress(){
        // given
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(Coordinate.withLattitudeLongtitude(1.0,1.0)))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));
        User user = registeredUser("userid","password", passwordEncoder);

        // when
        user.changeAddress(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0), addressHelper));
        UserModel userModel = user.toModel();

        // then
        assertNotNull(userModel.getAddress());
        Assertions.assertEquals(userModel.getAddress().getCity(),"서울특별시");
        Assertions.assertEquals(userModel.getAddress().getProvince(),"용산구");
        Assertions.assertEquals(userModel.getAddress().getDong(),"남영동");
    }

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal(){
        // given
        User user = registeredUser("userid","password", passwordEncoder);

        // when
        user.withdrawal(passwordEncoder, "password");
        UserModel userModel = user.toModel();

        // then
        assertEquals(userModel.getState(), UserState.WITHDRAWAL);
    }

    @Test
    @DisplayName("회원탈퇴시 비밀번호가 일치하지 않으면 안됨")
    void notEqPasswordWhenWithdrawal(){
        // given
        User user = registeredUser("userid","password", passwordEncoder);

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            user.withdrawal(passwordEncoder, "notEqPassword");
        });
    }

    @Test
    @DisplayName("이미 탈퇴한 회원은 주소지를 변경할 수 없음")
    void notAbleChangeAddressWhoAlreadyWithdrawalUser(){
        // given
        User user = registeredUser("userid","password", passwordEncoder);
        user.withdrawal(passwordEncoder, "password");

        // when
        assertThrows(IllegalStateException.class,()->{
            user.changeAddress(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0), mock(AddressHelper.class)));
        });
    }
}
