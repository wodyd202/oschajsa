package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.exception.UserNotFoundException;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.external.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.asList;

/**
 * 사용자 조회 서비스
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class QueryUserService implements UserDetailsService {
    private QueryUserRepository userRepository;

    // 외부 모듈
    private StoreRepository storeRepository;

    public UserModel getUserModel(String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // 자신의 업체 정보 추가
        userModel.addStoreInfo(storeRepository.getStore(userId));

        // 비밀번호 제거
        userModel.emptyPassword();
        return userModel;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if(userModel.isWithdrawal()){
            throw new UsernameNotFoundException(username);
        }
        return new User(userModel.getUserId(), userModel.getPassword(), asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
