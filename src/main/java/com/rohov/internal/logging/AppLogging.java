package com.rohov.internal.logging;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@Aspect
public class AppLogging {

    Logger log = LoggerFactory.getLogger(AppLogging.class);

    @Pointcut("execution(* com.rohov.internal.service.impl.*Impl.*(..))")
    public void userServiceMethods() {}

    @Before("userServiceMethods()")
    public void logUserServiceMethodsBefore(JoinPoint joinPoint) {
        log.info("Starts to executing method: {} ", joinPoint.getSignature().toString());
    }

    @AfterReturning(value = "userServiceMethods()", returning = "result")
    public void logUserServiceMethodsAfterReturning(JoinPoint joinPoint, Object result) {
        if (Objects.nonNull(result)) {
            log.info("Method '{}' - returned = {}", joinPoint.getSignature().getName(), result.toString());
        } else {
            log.info("Method '{}' - returned = void", joinPoint.getSignature().getName());
        }
    }

    @After(value = "userServiceMethods()")
    public void logUserServiceMethodsAfter(JoinPoint joinPoint) {
        log.info("Method {} finished successful !", joinPoint.getSignature().toString());
    }

    @AfterThrowing(value = "userServiceMethods()", throwing = "error")
    public void logUserServiceMethodsAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Method : {} finished with error : {}", joinPoint.getSignature().toString(), error.getMessage());
    }
}
