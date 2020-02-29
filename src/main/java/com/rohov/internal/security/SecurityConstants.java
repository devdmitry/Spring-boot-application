package com.rohov.internal.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    public static String TOKEN_HEADER;
    public static String TOKEN_PREFIX;
    public static String TOKEN_TYPE;
    public static String TOKEN_ISSUER;
    public static String TOKEN_AUDIENCE;

    public static Long verificationTokenExpiring;
    public static Long resetTokenExpiring;
    public static Long activeTokenExpiring;
    public static Long invationTokenExpiring;

    public enum Type {
        AUTHENTICATE,
        RESET,
        VERIFICATION,
        INVATION
    }

    @Value(value = "${spring.security.token-header}")
    public void setTOKEN_HEADER(String TOKEN_HEADER) {
        this.TOKEN_HEADER = TOKEN_HEADER;
    }
    @Value(value = "${spring.security.token-prefix}")
    public void setTOKEN_PREFIX(String TOKEN_PREFIX) {
        this.TOKEN_PREFIX = TOKEN_PREFIX;
    }
    @Value(value = "${spring.security.token-type}")
    public void setTOKEN_TYPE(String TOKEN_TYPE) {
        this.TOKEN_TYPE = TOKEN_TYPE;
    }
    @Value(value = "${spring.security.token-issuer}")
    public void setTOKEN_ISSUER(String TOKEN_ISSUER) {
        this.TOKEN_ISSUER = TOKEN_ISSUER;
    }
    @Value(value = "${spring.security.token-audience}")
    public void setTOKEN_AUDIENCE(String TOKEN_AUDIENCE) {
        this.TOKEN_AUDIENCE = TOKEN_AUDIENCE;
    }
    @Value(value = "${spring.security.secret-verification-expiring}")
    public void setVerificationTokenExpiring(Long verificationTokenExpiring) {
        this.verificationTokenExpiring = verificationTokenExpiring;
    }
    @Value(value = "${spring.security.secret-reset-expiring}")
    public void setResetTokenExpiring(Long resetTokenExpiring) {
        this.resetTokenExpiring = resetTokenExpiring;
    }
    @Value(value = "${spring.security.secret-active-expiring}")
    public void setActiveTokenExpiring(Long activeTokenExpiring) {
        this.activeTokenExpiring = activeTokenExpiring;
    }
    @Value(value = "${spring.security.secret-invation-expiring}")
    public void setInvationTokenExpiring(Long invationTokenExpiring) {
        this.invationTokenExpiring = invationTokenExpiring;
    }
}
