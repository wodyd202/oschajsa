package com.ljy.oschajsa.security;

import com.ljy.oschajsa.security.jwt.JwtAuthenticationFilter;
import com.ljy.oschajsa.security.jwt.JwtTokenProvider;
import com.ljy.oschajsa.security.jwt.JwtTokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private JwtTokenResolver jwtTokenResolver;

    @Bean
    PasswordEncoder passwordEncoder(){
        return createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/v1/users/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").authenticated()

                .antMatchers(HttpMethod.POST, "/api/v1/stores").authenticated()
                .antMatchers(HttpMethod.POST, "/api/v1/stores/interest/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/v1/stores/**").authenticated()

                .antMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()

                .antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenResolver,jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}