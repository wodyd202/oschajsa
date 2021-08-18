package com.ljy.oschajsa.oschajsa.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

@Slf4j
public class JwtAuthenticationFilter implements Filter {
    private final JwtTokenResolver jwtTokenResolver;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenResolver jwtTokenResolver, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenResolver = jwtTokenResolver;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = jwtTokenResolver.resolve((HttpServletRequest) servletRequest);
            jwtTokenProvider.validation(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (EmptyTokenException e) {
            log.error("empty token");
        } catch (InvalidTokenException e) {
            log.error("invalid token");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
