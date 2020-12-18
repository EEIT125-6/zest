package xun.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(* xun.controller.*.*(..))")
	public void pointcut() {
		
	}
	
//	@Before("pointcut()")
	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("star");
//		System.out.println("before!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(joinPoint.getSignature().getName());
//		System.out.println("END!!!!!!!!!!!!!!!!!!");
	}
	
	@After("pointcut()")
	public void after(JoinPoint joinPoint) {
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("end");
	}
}
