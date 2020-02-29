package com.rohov.internal.service;

import com.rohov.internal.entity.User;

public interface EmailService {

    void sendResetToken(User user);

    void sendVerificationToken(User user);

    void sendInvationToken(String email, String token);
}
