package com.ljy.oschajsa.services.user.query.application;

import com.ljy.oschajsa.services.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static java.util.Arrays.asList;


@Service
@AllArgsConstructor
public class QueryUserService implements UserDetailsService {
    @Autowired private QueryUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if(userModel.isWithdrawal()){
            throw new UsernameNotFoundException(username);
        }
        return new User(userModel.getUserId(), userModel.getPassword(), asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
