package com.ljy.oschajsa.services.user.command.application;

import com.ljy.oschajsa.services.user.command.application.exception.AlreadyExistUserException;
import com.ljy.oschajsa.services.user.domain.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import com.ljy.oschajsa.services.user.domain.UserRepository;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.command.application.model.RegisterUser;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 등록 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * @param registerUser
     * # 사용자 등록
     */
    public UserModel register(final RegisterUser registerUser) {
        verifyNotExistUser(registerUser.getId());
        User user = userMapper.mapFrom(registerUser);

        userRepository.save(user);

        return user.toModel();
    }

    private void verifyNotExistUser(String id) {
        if(userRepository.findById(UserId.of(id)).isPresent()){
            throw new AlreadyExistUserException();
        }
    }
}
