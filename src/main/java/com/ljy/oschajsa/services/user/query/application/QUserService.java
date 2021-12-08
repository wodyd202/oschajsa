package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.user.query.model.*;
import com.ljy.oschajsa.services.user.query.model.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class QUserService implements UserDetailsService {
    private final QUserRepository userRepository;
    private final QStoreRepository storeRepository;
    private final QInterestStoreRepository interestStoreRepository;

    public QUserService(QUserRepository userRepository, QStoreRepository storeRepository, QInterestStoreRepository interestStoreRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.interestStoreRepository = interestStoreRepository;
    }

    public QueryAddress getAddress(String userId) {
        return userRepository.findAddressByUserId(userId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<QueryStore> getStore(String userId) {
        return storeRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Set<String> getInterestStores(String userId) {
        return interestStoreRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryUser queryUser = userRepository.login(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(queryUser.getUserId(), queryUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER")));
    }

}
