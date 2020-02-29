package com.rohov.internal.service;

import com.rohov.internal.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    User getByToken(String token);

    User findById(Long id);

    User create(User user);

    User completeProfile(String token, String password, String name, String position);

    void verificateUser(String token);

    void sendResetToken(String email);

    User makeNewPassword(String resetToken, String newPassword);

}

