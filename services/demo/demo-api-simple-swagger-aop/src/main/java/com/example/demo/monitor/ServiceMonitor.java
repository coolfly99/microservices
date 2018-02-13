package com.example.demo.monitor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

	private static final Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);

	@Around("execution(* com..*Service.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object obj = joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;

		logger.info("Execute {} took {}ms", joinPoint, timeTaken);
		return obj;
	}

	@Before("execution(* com..*Service.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("Check for service access ");
		logger.info("Allowed execution for {}", joinPoint);
	}

	@AfterReturning("execution(* com..*Service.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		logger.info("Completed: " + joinPoint);
	}
}
