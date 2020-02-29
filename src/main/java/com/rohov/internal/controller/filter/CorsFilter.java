package com.rohov.internal.controller.filter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsFilter implements Filter {

    @Value("${localhost.url}")
    String LOCALHOST_URL;

    List<String> allowedOrigins;

    @PostConstruct
    private void init() {
        allowedOrigins = Arrays.asList(
                LOCALHOST_URL
        );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = httpRequest.getHeader("Origin");

        httpResponse.addHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "*");
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, OPTIONS, DELETE");
        httpResponse.addHeader("Access-Control-Max-Age", "1800");
        httpResponse.addHeader("Access-Control-Allow-Headers", "Content-Type, User-Agent, " +
                "X-Request-With, Cache-Control, Accept, Origin, Authorization");
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }
}
