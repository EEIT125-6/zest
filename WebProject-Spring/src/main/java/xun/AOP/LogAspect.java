package xun.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import xun.controller.StoreR_Controller;

@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(* xun.controller.*.*(..))")
	public void pointcut() {
		
	}
	
	
	@Before("pointcut()")
	public void before(JoinPoint joinPoint) {
		Logger logger = Logger.getLogger(StoreR_Controller.class);
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("star");
//		System.out.println("before!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(joinPoint.getSignature().getName());
//		System.out.println("END!!!!!!!!!!!!!!!!!!");
		logger.info("print STAR BEFORE!!!");
		System.out.println("BEFORE!");
		logger.info("SS");
	}
	
	@After("pointcut()")
	public void after(JoinPoint joinPoint) {
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("end");
		Logger.getLogger(getClass()).info("WTF");
	}
}
