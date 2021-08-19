package com.ljy.oschajsa.oschajsa.user;

import com.ljy.oschajsa.oschajsa.core.object.Address;
import com.ljy.oschajsa.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.core.service.AddressHelper;
import com.ljy.oschajsa.oschajsa.user.command.service.*;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {

    @DisplayName("사용자 아이디는 null값을 허용하지 않음")
    @Test
    void nullUserId(){
        assertThrows(NullPointerException.class, ()->{
            UserId.of(null);
        });
    }

    @DisplayName("사용자 아이디는 빈값을 허용하지 않음")
    void emptyUserId(){
        assertThrows(InvalidUserIdException.class,()->{
           UserId.of("");
        });
    }

    @DisplayName("사용자 아이디는 영어[소문자], 숫자만 허용하고 4자 이상 15자 이하로 입력해야하며, 첫 글자는 반드시 영문이여야한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", " test3", " 3test", "test 3","test3 "})
    void invalidUserId(String id){
        assertThrows(InvalidUserIdException.class, ()->{
           UserId.of(id);
        });
    }

    @Test
    @DisplayName("사용자 아이디 정상 입력")
    void validUserId(){
        UserId userId = UserId.of("test3");
        assertEquals(userId, UserId.of("test3"));
        assertEquals(userId.get(), "test3");
    }

    @DisplayName("사용자 비밀번호는 null값을 허용하지 않음")
    @Test
    void nullPassword(){
         assertThrows(NullPointerException.class,()->{
            Password.of(null);
         });
    }

    @DisplayName("사용자 비밀번호는 빈값을 허용하지 않음")
    @Test
    void emptyPassword(){
        assertThrows(InvalidPasswordException.class, ()->{
           Password.of("");
        });
    }

    @DisplayName("사용자 비밀번호는 공백을 제외한 숫자, 영문[대,소문자], 특수문자(._%+=)만 허용하며, 8자 이상 15자 이하만 허용")
    @ParameterizedTest
    @ValueSource(strings = {"테스트"," password","password ","pass word"})
    void invalidPassword(String pw){
        assertThrows(InvalidPasswordException.class, ()->{
            Password.of(pw);
        });
    }

    @Test
    @DisplayName("사용자 비밀번호 정상 입력")
    void validPassword(){
        Password password = Password.of("password");
        assertEquals(password, Password.of("password"));
        assertEquals(password.get(), "password");
    }

    @Test
    @DisplayName("사용자 닉네임은 null을 허용하지 않음")
    void nullNickName(){
        assertThrows(NullPointerException.class,()->{
           NickName.of(null);
        });
    }

    @Test
    @DisplayName("사용자 닉네임은 빈값을 허용하지 않음")
    void emptyNickname(){
        assertThrows(InvalidNicknameException.class,()->{
            NickName.of("");
        });
    }

    @DisplayName("사용자 닉네임은 한글, 영어[대,소문자], 숫자만 허용하고 3자 이상 10자 이하여야함")
    @ParameterizedTest
    @ValueSource(strings = {"ㅌㅅㅌ","테스트 "," 테스트","테스트%","테스트@","TEST "})
    void invalidNickName(String nickname){
        assertThrows(InvalidNicknameException.class,()->{
           NickName.of(nickname);
        });
    }

    @DisplayName("사용자 닉네임 정상 입력")
    @Test
    void validNickname(){
        NickName nickname = NickName.of("test");
        assertEquals(nickname, NickName.of("test"));
        assertEquals(nickname.get(), "test");
    }

    @Test
    @DisplayName("사용자 생성시 주소 좌표값을 입력하지 않을 경우 Address가 null값이됨")
    void emptyCoordinate(){
        // 사용자 생성시 주소는 입력하지 않아도 됨
        RegisterUser registerUser = RegisterUser.builder()
                .id("userid")
                .password("password")
                .nickname("nickname")
                .build();

        AddressHelper addressHelper = mock(AddressHelper.class);
        UserMapper userMapper = new UserMapper(addressHelper);
        User user = userMapper.mapFrom(registerUser);
        assertEquals(user.getUserId(), UserId.of("userid"));
        assertEquals(user.getNickname(), NickName.of("nickname"));
        assertNull(user.getAddress());
    }

    @Test
    void containCoordinate(){
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

        UserMapper userMapper = new UserMapper(addressHelper);
        User user = userMapper.mapFrom(registerUser);
        assertNotNull(user.getAddress());
        assertEquals(user.getAddress().getAddressInfo().getCity(),"서울특별시");
    }

    @Test
    @DisplayName("사용자 주소지 변경")
    void changeAddress(){
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(Coordinate.withLattitudeLongtitude(1.0,1.0)))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        User user = UserFixture.aUser().build();
        user.changeAddress(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0), addressHelper));
        assertNotNull(user.getAddress());
        assertEquals(user.getAddress().getAddressInfo().getCity(),"서울특별시");
    }

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        user.encodePassword(passwordEncoder);
        user.withdrawal(passwordEncoder, "password");
        assertEquals(user.getState(), UserState.WITHDRAWAL);
    }

    @Test
    @DisplayName("회원탈퇴시 비밀번호가 일치하지 않으면 안됨")
    void notEqPasswordWhenWithdrawal(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        user.encodePassword(passwordEncoder);
        assertThrows(InvalidPasswordException.class, ()->{
            user.withdrawal(passwordEncoder, "notEqPassword");
        });
    }

    @Test
    @DisplayName("이미 탈퇴한 회원은 주소지를 변경할 수 없음")
    void notAbleChangeAddressWhoAlreadyWithdrawalUser(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        user.encodePassword(passwordEncoder);
        user.withdrawal(passwordEncoder, "password");

        assertThrows(AlreadyWithdrawalUserException.class,()->{
            user.changeAddress(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0), mock(AddressHelper.class)));
        });
    }

    @Test
    @DisplayName("비밀번호 암호화")
    void encodePassword(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.encodePassword(delegatingPasswordEncoder);
        assertTrue(delegatingPasswordEncoder.matches("password", user.getPassword().get()));
    }

    @Nested
    @DisplayName("사용자 생성 테스트")
    class RegisterUserServiceTest {
        UserRepository userRepository = mock(UserRepository.class);
        RegisterUserService service = new RegisterUserService(userRepository, new UserMapper(mock(AddressHelper.class)), mock(PasswordEncoder.class), mock(ApplicationEventPublisher.class));

        @Test
        @DisplayName("사용자 생성")
        void register(){
            RegisterUser registerUser = RegisterUser.builder()
                    .id("userid")
                    .password("password")
                    .nickname("nickname")
                    // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
                    .build();

            assertDoesNotThrow(()->{
                service.register(registerUser);
            });
        }

        @Test
        @DisplayName("이미 가입한 아이디가 존재함")
        void alreadyExistUser(){
            when(userRepository.findByUserId(UserId.of("userid")))
                    .thenReturn(Optional.of(mock(User.class)));

            RegisterUser registerUser = RegisterUser.builder()
                    .id("userid")
                    .password("password")
                    .nickname("nickname")
                    // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
                    .lettitude(1.0)
                    .longtitude(1.0)
                    .build();

            assertThrows(AlreadyExistUserException.class,()->{
                service.register(registerUser);
            });
        }
    }

    @Nested
    class ChangeAddressServiceTest {
        AddressHelper addressHelper = mock(AddressHelper.class);
        UserRepository userRepository = mock(UserRepository.class);
        ChangeAddressService service = new ChangeAddressService(userRepository, addressHelper, mock(ApplicationEventPublisher.class));

        @Test
        @DisplayName("사용자 주소 변경시 해당 사용자가 존재하지 않으면 안됨")
        void notExistUser(){
            UserId userid = UserId.of("userid");
            ChangeAddress changeAddress = ChangeAddress.builder()
                    .lettitude(1.0)
                    .longtitude(1.1)
                    .build();
            assertThrows(UserNotFoundException.class,()->{
                service.changeAddress(changeAddress, userid);
            });
        }

        @Test
        @DisplayName("사용자 주소 변경")
        void changeAddress(){
            when(addressHelper.getAddressInfoFrom(Coordinate.withLattitudeLongtitude(1.0,1.1)))
                    .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

            User user = UserFixture.aUser().build();
            when(userRepository.findByUserId(UserId.of("userid")))
                    .thenReturn(Optional.of(user));

            UserId userid = UserId.of("userid");
            ChangeAddress changeAddress = ChangeAddress.builder()
                    .lettitude(1.0)
                    .longtitude(1.1)
                    .build();

            assertDoesNotThrow(()->{
                service.changeAddress(changeAddress, userid);
            });
        }
    }

    @Nested
    class WithdrawalServiceTest {
        UserRepository userRepository = mock(UserRepository.class);
        WithdrawalService service = new WithdrawalService(userRepository, passwordEncoder, mock(ApplicationEventPublisher.class));

        @Test
        @DisplayName("회원 탈퇴시 해당 아이디가 존재하지 않으면 안됨")
        void notExistUser() {
            UserId userid = UserId.of("userid");
            WithdrawalUser withdrawalUser = WithdrawalUser.builder().build();
            assertThrows(UserNotFoundException.class,()->{
                service.withdrawal(withdrawalUser, userid);
            });
        }

        @Test
        @DisplayName("회원 탈퇴")
        void withdrawal(){
            User user = UserFixture.aUser().password(Password.of("password")).build();
            user.encodePassword(passwordEncoder);
            when(userRepository.findByUserId(UserId.of("userid")))
                    .thenReturn(Optional.of(user));

            UserId userid = UserId.of("userid");
            WithdrawalUser withdrawalUser = WithdrawalUser.builder()
                    .originPassword("password")
                    .build();
            assertDoesNotThrow(()->{
                service.withdrawal(withdrawalUser, userid);
            });
        }
    }

}