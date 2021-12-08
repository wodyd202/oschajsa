package com.ljy.oschajsa.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private JwtTokenResolver jwtTokenResolver;
    private JwtTokenProvider jwtTokenProvider;

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
