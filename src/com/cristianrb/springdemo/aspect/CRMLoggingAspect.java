package com.cristianrb.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.cristianrb.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.cristianrb.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("execution(* com.cristianrb.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("forControllerPackage() || forDAOPackage() || forServicePackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		String method = theJoinPoint.getSignature().toShortString();
		myLogger.info("In @Before, calling method: " + method);
	}
}
