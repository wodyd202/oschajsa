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
    private CacheQueryUserRepository cacheUserRepository;
    private QueryUserRepository userRepository;

    // 외부 모듈
    private StoreRepository storeRepository;

    public UserModel getUserModel(String userId) {
        // 레디스에서 먼저 조회 후 없으면 DB에서 조회
        // DB에도 없으면 에러
        UserModel userModel = getModel(userId);

        // 자신의 업체 정보 추가
        userModel.addStoreInfo(storeRepository.getStore(userId));

        // 비밀번호 제거
        userModel.emptyPassword();
        return userModel;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 레디스에서 먼저 조회 후 없으면 DB에서 조회
        // DB에도 없으면 에러
        UserModel userModel = getModel(username);

        if(userModel.isWithdrawal()){
            throw new UsernameNotFoundException(username);
        }
        return new User(userModel.getUserId(), userModel.getPassword(), asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private UserModel getModel(String userId) {
        UserModel userModel = cacheUserRepository.findById(userId).orElseGet(()->{
            // 존재하지 않을 경우 DB에도 없으면 에러 그렇지 않으면 레디스에 저장
            UserModel userModelFromDB = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            cacheUserRepository.save(userModelFromDB);
            return userModelFromDB;
        });
        return userModel;
    }
}
