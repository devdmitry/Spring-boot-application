package com.rohov.internal.configuration;

import com.rohov.internal.controller.filter.CorsFilter;
import com.rohov.internal.security.TokenManager;
import com.rohov.internal.security.filters.AuthorizationFilter;
import com.rohov.internal.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    UserService userService;
    TokenManager tokenManager;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/*", "/api/v1/companies/search").permitAll()

                .antMatchers(HttpMethod.POST,
                        "/api/v1/companies").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.PUT,
                        "/api/v1/companies/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.PATCH,
                        "/api/v1/companies/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.DELETE,
                        "/api/v1/companies/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/owned").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/*").authenticated()

                .antMatchers(HttpMethod.POST,
                        "/api/v1/companies/*/users").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/*/users").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/*/users/*").authenticated()

                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/*/projects").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/companies/*/projects/*").authenticated()
                .antMatchers(HttpMethod.POST,
                        "/api/v1/companies/*/projects/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.PUT,
                        "/api/v1/companies/*/projects/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.PATCH,
                        "/api/v1/companies/*/projects/*").access("hasRole('MANAGER')")
                .antMatchers(HttpMethod.DELETE,
                        "/api/v1/companies/*/projects/*").access("hasRole('MANAGER')")

                .antMatchers(HttpMethod.POST,
                        "/api/v1/projects/*/users/*/permissions").access("hasRole('MANAGER')")

                .antMatchers(HttpMethod.GET,
                        "/api/v1/projects/*/tasks").hasAnyRole("MANAGER", "READ", "WRITE")
                .antMatchers(HttpMethod.GET,
                        "/api/v1/projects/*/tasks/*").hasAnyRole("MANAGER", "READ", "WRITE")
                .antMatchers(HttpMethod.POST,
                        "/api/v1/projects/*/tasks").hasAnyRole("MANAGER", "WRITE")
                .antMatchers(HttpMethod.PUT,
                        "/api/v1/projects/*/tasks/*").hasAnyRole("MANAGER", "WRITE")
                .antMatchers(HttpMethod.PATCH,
                        "/api/v1/projects/*/tasks/*").hasAnyRole("MANAGER", "WRITE")
                .antMatchers(HttpMethod.DELETE,
                        "/api/v1/projects/*/tasks/*").hasAnyRole("MANAGER", "WRITE")

                .antMatchers(HttpMethod.GET,
                        "/api/v1/projects/*/tasks/*/subscribers")
                .hasAnyRole("MANAGER", "WRITE", "READ")
                .antMatchers(HttpMethod.POST,
                        "/api/v1/projects/*/tasks/*/subscribers").hasAnyRole("MANAGER", "WRITE")
                .antMatchers(HttpMethod.DELETE,
                        "/api/v1/projects/*/tasks/*/subscribers").hasAnyRole("MANAGER", "WRITE")

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), tokenManager, userService));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/public/**")
                .antMatchers("/app.js")
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/api/v1/auth/sign-in")
                .antMatchers("/websocket-example")
                .antMatchers("/websocket-example/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    CorsFilter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
