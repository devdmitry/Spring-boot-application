package com.rohov.internal.security.filters;

import com.rohov.internal.entity.User;
import com.rohov.internal.security.SecurityConstants;
import com.rohov.internal.security.TokenManager;
import com.rohov.internal.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import liquibase.util.StringUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    TokenManager tokenManager;
    UserService userService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenManager tokenManager,
                               UserService userService) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        UsernamePasswordAuthenticationToken auth = getAuthentication(request);

        if (auth == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            chain.doFilter(request,response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {

                byte[] signingKey = tokenManager.getSecretKey().getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

                String line = parsedToken.getBody().getSubject();
                String email = line.split("::")[0];

                User user = (User) userService.loadUserByUsername(email);

                List<SimpleGrantedAuthority> authorities = user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
                        .collect(Collectors.toList());

                authorities.addAll(getProjectAuthorities(request.getRequestURI(), user));

                if (StringUtils.isNotEmpty(email)) {
                    return new UsernamePasswordAuthenticationToken(email, null, authorities);
                }
            } catch (RuntimeException exception) {
                log.warn("Error : {} - {}", token, exception.getMessage());
            }

        }
        return null;
    }

    private Collection<? extends SimpleGrantedAuthority> getProjectAuthorities(String requestUri, User user) {
        Pattern pattern = Pattern.compile("/projects/([0-9]+)");
        Matcher m = pattern.matcher(requestUri);
        Long projectId;
        if (m.find()) {
            projectId = Long.parseLong(m.group(1));
            if (Objects.nonNull(user.getProjectPermissions()))
                return user.getProjectPermissions().stream()
                                .filter(userProjectPermission -> userProjectPermission.getProject()
                                        .getId()
                                        .equals(projectId))
                                .map(userProjectPermission ->
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + userProjectPermission.getPermission().getName()
                                        ))
                                .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}

