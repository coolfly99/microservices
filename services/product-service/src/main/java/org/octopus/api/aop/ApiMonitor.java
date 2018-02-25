package org.octopus.api.aop;

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
public class ApiMonitor {

	private static final Logger logger = LoggerFactory.getLogger(ApiMonitor.class);

	@Around("execution(* org.octopus..*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object obj = joinPoint.proceed();

		long timeTaken = System.currentTimeMillis() - startTime;

		logger.info("Executed {} took {}ms", joinPoint, timeTaken);
		if (logger.isDebugEnabled())
			logger.debug("{} returned with value {}", joinPoint, obj);

		return obj;
	}

	@Before("execution(* org.octopus..*Controller.*(..))")
	public void before(JoinPoint joinPoint) {
		logger.info("Check for api access ");
		logger.info("Allowed execution for {}", joinPoint);
	}

	@AfterReturning("execution(* org.octopus..*Controller.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		logger.info("Completed: " + joinPoint);
	}
}
