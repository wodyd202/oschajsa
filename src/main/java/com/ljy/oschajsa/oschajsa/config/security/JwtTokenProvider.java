package com.ljy.oschajsa.oschajsa.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.toList;

@Component
public class JwtTokenProvider {
    @Autowired private UserDetailsService userDetailsService;

    @Value("${jwt.secretkey}")
    private String secretKey;

    @Value("${jwt.expire}")
    private long jwtExpire;

    @PostConstruct
    void setUp(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", authorities.stream().map(c->c.getAuthority()).collect(toList()));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpire))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void validation(String token) throws InvalidTokenException {
        try{
            Jws<Claims> headerClaimsJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            boolean expire = headerClaimsJwt.getBody().getExpiration().before(new Date());
            if(expire){
                throw new InvalidTokenException();
            }
        }catch (Exception e){
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}