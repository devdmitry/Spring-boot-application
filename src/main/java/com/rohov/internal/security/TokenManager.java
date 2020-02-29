package com.rohov.internal.security;

import com.rohov.internal.entity.Role;
import com.rohov.internal.entity.User;
import com.rohov.internal.exception.BusinessException;
import com.rohov.internal.exception.ErrorMessages;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TokenManager {

    @Value(value = "${spring.security.secret}")
    String secretKey;
    @Value(value = "${spring.security.secret-reset}")
    String resetSecretKey;
    @Value(value = "${spring.security.secret-verification}")
    String verificationSecretKey;
    @Value(value = "${spring.security.secret-invation}")
    String invationSecretKey;


    public String createToken(User user, Long expirationMills, SecurityConstants.Type type) {
        String key = getSecretKeyByActionType(type);
        List<String> roles = getRoles(user);
        byte[] signKey = key.getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(String.format("%s::%s", user.getUsername(), System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMills))
                .claim("roles", roles)
                .compact();
    }

    public String createInvationToken(String email, Long companyId, List<Role> roles) {
        String key = getSecretKeyByActionType(SecurityConstants.Type.INVATION);
        byte[] signKey = key.getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(String.format("%s::%d", email, companyId))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.invationTokenExpiring))
                .claim("roles", roles.stream().map(role -> role.getAuthority()).collect(Collectors.toList()))
                .compact();
    }

    private String getSecretKeyByActionType(SecurityConstants.Type type) {
        switch (type) {
            case AUTHENTICATE: return secretKey;
            case RESET: return resetSecretKey;
            case VERIFICATION: return verificationSecretKey;
            case INVATION: return invationSecretKey;
            default: throw new BusinessException(ErrorMessages.TYPE_OF_REQUEST_ACTION);
        }
    }

    private List<String> getRoles(User user) {
        if (user.getAuthorities() != null) {
            return user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
