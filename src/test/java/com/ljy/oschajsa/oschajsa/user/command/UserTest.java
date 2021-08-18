package com.ljy.oschajsa.oschajsa.user.command;

import com.ljy.oschajsa.oschajsa.user.command.domain.*;
import com.ljy.oschajsa.oschajsa.user.command.service.*;
import com.ljy.oschajsa.oschajsa.user.command.service.model.ChangeAddress;
import com.ljy.oschajsa.oschajsa.user.command.service.model.RegisterUser;
import com.ljy.oschajsa.oschajsa.user.command.service.model.WithdrawalUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    @DisplayName("사용자 주소 좌표는 모두 입력해야함")
    void emptyLongtitude(){
        assertThrows(InvalidAddressException.class, ()->{
           Coordinate.withLattitudeLongtitude(0.0, null);
        });
    }

    @Test
    @DisplayName("사용자 주소 좌표 정상 입력")
    void validCoordinate(){
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(0.0, 1.1);
        assertEquals(coordinate, Coordinate.withLattitudeLongtitude(0.0, 1.1));
        assertEquals(coordinate.getLettitude(), 0.0);
        assertEquals(coordinate.getLongtitude(), 1.1);
    }

    @Test
    @DisplayName("입력받은 좌표값을 통해 사용자 주소 시,도,동 정보를 가져옴")
    void getAddressInfo(){
        // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(0.0, 1.1);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        AddressInfo info = coordinate.getAddressInfo(addressHelper);
        assertEquals(info, AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));
        assertEquals(info.getCity(), "서울특별시");
        assertEquals(info.getProvince(), "용산구");
        assertEquals(info.getDong(), "남영동");
    }

    @Test
    @DisplayName("좌표값이 유효해야함")
    void invalidCoordinate(){
        // 임의 유효하지 않은 좌표값
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(1.0, 2.0);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenThrow(InvalidAddressException.class);
        assertThrows(InvalidAddressException.class,()->{
            coordinate.getAddressInfo(addressHelper);
        });
    }

    @Test
    @DisplayName("좌표가 유효하여 사용자 주소지 생성")
    void validAddress(){
        Coordinate coordinate = Coordinate.withLattitudeLongtitude(1.0, 2.0);
        AddressHelper addressHelper = mock(AddressHelper.class);
        when(addressHelper.getAddressInfoFrom(coordinate))
                .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));

        Address address = Address.withCoodinate(coordinate, addressHelper);
        assertEquals(address.getCoordinate(), coordinate);
        assertEquals(address.getAddressInfo(), AddressInfo.withCityProvinceDong("서울특별시","용산구","남영동"));
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

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        user.withdrawal(Password.of("password"));
        assertEquals(user.getState(), UserState.WITHDRAWAL);
    }

    @Test
    @DisplayName("회원탈퇴시 비밀번호가 일치하지 않으면 안됨")
    void notEqPasswordWhenWithdrawal(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        assertThrows(InvalidPasswordException.class, ()->{
            user.withdrawal(Password.of("notEqPassword"));
        });
    }

    @Test
    @DisplayName("이미 탈퇴한 회원은 주소지를 변경할 수 없음")
    void notAbleChangeAddressWhoAlreadyWithdrawalUser(){
        User user = UserFixture.aUser()
                .password(Password.of("password")).build();
        user.withdrawal(Password.of("password"));

        assertThrows(AlreadyWithdrawalUserException.class,()->{
            user.changeAddress(Address.withCoodinate(Coordinate.withLattitudeLongtitude(1.0,1.0), mock(AddressHelper.class)));
        });
    }

    @Nested
    @DisplayName("사용자 생성 테스트")
    class RegisterUserServiceTest {
        UserRepository userRepository = mock(UserRepository.class);
        RegisterUserService service = new RegisterUserService(userRepository, new UserMapper(mock(AddressHelper.class)));

        @Test
        @DisplayName("사용자 생성")
        void register(){
            RegisterUser registerUser = RegisterUser.builder()
                    .id("userid")
                    .password("password")
                    .nickname("nickname")
                    // 임의 좌표값 이 좌표를 서울역 좌표로 가정함
                    .lettitude(1.0)
                    .longtitude(1.0)
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
        UserRepository userRepository = mock(UserRepository.class);
        ChangeAddressService service = new ChangeAddressService(userRepository, mock(AddressHelper.class));

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
            when(userRepository.findByUserId(UserId.of("userid")))
                    .thenReturn(Optional.of(mock(User.class)));

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
        WithdrawalService service = new WithdrawalService(userRepository);

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
            when(userRepository.findByUserId(UserId.of("userid")))
                    .thenReturn(Optional.of(UserFixture.aUser().password(Password.of("password")).build()));

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
