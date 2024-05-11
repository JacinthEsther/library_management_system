package com.techie.librarymanagementsystem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.techie.librarymanagementsystem.services.*.*(..))")
    private void serviceLayerExecution() {}

    @Before("serviceLayerExecution()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Method called: {}", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "serviceLayerExecution()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }

    @Before("execution(* com.techie.librarymanagementsystem.services.interfaceImpl.BookServiceImpl.addBook(..))")
    public void logBookAddition(JoinPoint joinPoint) {
        logger.info("Adding a new book...");
    }

    @Before("execution(* com.techie.librarymanagementsystem.services.interfaceImpl.BookServiceImpl.updateBook(..))")
    public void logBookUpdate(JoinPoint joinPoint) {
        logger.info("Updating a book...");
    }

    @Before("execution(* com.techie.librarymanagementsystem.services.interfaceImpl.PatronServiceImpl.*(..))")
    public void logPatronTransaction(JoinPoint joinPoint) {
        logger.info("Performing a patron transaction...");
    }

    @Around("execution(* com.techie.librarymanagementsystem.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        logger.info("Execution time of {}: {} ms", joinPoint.getSignature().toShortString(), stopWatch.getTotalTimeMillis());
        return proceed;
    }
}