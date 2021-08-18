package com.ljy.oschajsa.oschajsa.user.query.service;

import com.ljy.oschajsa.oschajsa.user.query.model.QueryUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class QUserService implements UserDetailsService {
    private final QUserRepository userRepository;

    public QUserService(QUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryUser queryUser = userRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(queryUser.getUserId(), queryUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER")));
    }
}
