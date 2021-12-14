package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.exception.UserNotFoundException;
import com.ljy.oschajsa.services.user.domain.model.UserModel;
import com.ljy.oschajsa.services.user.query.application.external.InterestRepository;
import com.ljy.oschajsa.services.user.query.application.external.StoreRepository;
import com.ljy.oschajsa.services.user.query.application.model.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
public class UserSearchService implements UserDetailsService {
    private final UserRepository userRepository;

    // 외부 모듈
    private final StoreRepository storeRepository;
    private final InterestRepository interestRepository;

    /**
     * @param userId
     * # 사용자 정보 조회
     */
    @Cacheable(value = "user", key = "#userId")
    public UserResponse getUserModel(final String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userModel.emptyPassword();
        return UserResponse.builder()
                .user(userModel)
                // 자신의 관심 업체 10개 조회
                .interestStores(interestRepository.getInterests(userId))

                // 자신의 업체 조회
                .stores(storeRepository.getStore(userId))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if(userModel.isWithdrawal()){
            throw new UsernameNotFoundException(userId);
        }
        return new User(userModel.getUserId(), userModel.getPassword(), asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
