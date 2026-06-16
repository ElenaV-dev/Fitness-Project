package com.fitness.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.fitness.service.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        LOGGER.info("START {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning( "execution(* com.fitness.service.impl.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        LOGGER.info("END {}", joinPoint.getSignature().toShortString());
    }
}
