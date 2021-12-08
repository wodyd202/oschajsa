package com.ljy.oschajsa.config.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenResolver {
    public String resolve(HttpServletRequest request) throws EmptyTokenException {
        String token = request.getHeader("X-AUTH-TOKEN");
        if(!StringUtils.hasText(token)){
            throw new EmptyTokenException();
        }
        return token;
    }
}