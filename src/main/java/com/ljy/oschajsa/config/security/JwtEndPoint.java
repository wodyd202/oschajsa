package com.ljy.oschajsa.config.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtEndPoint {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;

    public JwtEndPoint(JwtTokenProvider jwtTokenProvider, UserDetailsService userService, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("oauth/token")
    public ResponseEntity<?> oauthToken(@RequestParam String username,
                                        @RequestParam String password){
        if(!StringUtils.hasText(username)){
            return ResponseEntity.badRequest().body("username must not be empty");
        }
        if(!StringUtils.hasText(password)){
            return ResponseEntity.badRequest().body("password must not be empty");
        }
        try{
            UserDetails userDetails = userService.loadUserByUsername(username);
            String token = jwtTokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities());
            return ResponseEntity.ok(token);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.badRequest().body("invalid username and password");
        }
    }
}