package com.rohov.internal.service.impl;

import com.rohov.internal.entity.Role;
import com.rohov.internal.entity.User;
import com.rohov.internal.entity.UserCompany;
import com.rohov.internal.entity.UserProfile;
import com.rohov.internal.exception.ErrorMessages;
import com.rohov.internal.exception.NotFoundException;
import com.rohov.internal.repository.jpa.UserCompanyRepository;
import com.rohov.internal.repository.jpa.UserRepository;
import com.rohov.internal.security.SecurityConstants;
import com.rohov.internal.security.TokenManager;
import com.rohov.internal.service.CompanyService;
import com.rohov.internal.service.EmployeePositionService;
import com.rohov.internal.service.RoleService;
import com.rohov.internal.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserCompanyRepository userCompanyRepository;
    RoleService roleService;
    CompanyService companyService;
    EmployeePositionService employeePositionService;
    TokenManager tokenManager;
    EmailServiceImpl emailService;
    MessageSendingOperations<String> template;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final String errorMessage = String.format(ErrorMessages.USER_WRONG_EMAIL, email);
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(errorMessage));
    }

    @Override
    public User getByToken(String token) {
        byte[] signingKey = tokenManager.getSecretKey().getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

        String body = parsedToken.getBody().getSubject();
        String email = body.split("::")[0];

        return (User) loadUserByUsername(email);
    }

    @Override
    public User findById(Long id) {
        final String errMsg = String.format(ErrorMessages.USER_WRONG_ID, id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(errMsg));
    }

    @Transactional
    public User create(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.getUserProfile().setId(user.getId());

        String token = tokenManager.createToken(user, SecurityConstants.verificationTokenExpiring,
                SecurityConstants.Type.VERIFICATION);

        user.setVerificationToken(token);

        user.setRoles(user.getRoles()
                .stream()
                .map(role -> roleService.findByName(role.getName()))
                .collect(Collectors.toSet()));

        user = userRepository.save(user);

        emailService.sendVerificationToken(user);

        return user;
    }

    @Transactional
    @Override
    public User completeProfile(String token, String password, String name, String position) {
        byte[] signingKey = tokenManager.getInvationSecretKey().getBytes();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
        String body = parsedToken.getBody().getSubject();

        Set<Role> roles = ((List<?>) parsedToken.getBody()
                .get("roles")).stream()
                .map(authority -> roleService.findByName(authority.toString()))
                .collect(Collectors.toSet());

        String email = body.split("::")[0];
        Long companyId = Long.parseLong(body.split("::")[1]);

        User user = new User();
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setIsVerified(true);
        user.setRoles(roles);

        user.setUserProfile(new UserProfile());
        user.getUserProfile().setId(user.getId());
        user.getUserProfile().setName(name);
        user.getUserProfile().setEmployeePosition(employeePositionService.find(position));

        UserCompany userCompany = new UserCompany();
        userCompany.setUser(user);
        userCompany.setCompany(companyService.find(companyId));

        userRepository.save(user);
        userCompanyRepository.save(userCompany);

        return user;
    }

    @Override
    public void verificateUser(String token) {
        final String errorMessage = String.format(ErrorMessages.USER_WRONG_TOKEN, token);
        User user = userRepository
                .findByVerificationToken(token)
                .orElseThrow(() -> new NotFoundException(errorMessage));

        user.setIsVerified(true);

        userRepository.save(user);
    }

    @Override
    public void sendResetToken(String email) {
        User user = (User) loadUserByUsername(email);

        String token = tokenManager.createToken(user, SecurityConstants.resetTokenExpiring,
                SecurityConstants.Type.RESET);

        user.setResetToken(token);

        userRepository.save(user);
        emailService.sendResetToken(user);
    }

    @Override
    public User makeNewPassword(String resetToken, String newPassword) {
        final String errorMessage = String.format(ErrorMessages.USER_WRONG_TOKEN, resetToken);
        User user = userRepository
                .findByResetToken(resetToken)
                .orElseThrow(() -> new NotFoundException(errorMessage));

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));

        return userRepository.save(user);
    }

    private void sendCreatedNotification(UserProfile information) {
        String destination = "/topic/" + information.getName();
        template.convertAndSend(destination, information);
    }


}
