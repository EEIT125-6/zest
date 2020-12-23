package xun.AOP;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import xun.controller.ProductCUD_Controller;
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
	
	@After("execution(* xun.controller.ProductCUD_Controller.*(..))")
	public void after(JoinPoint joinPoint) {
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("end");
//		Logger.getLogger(getClass()).info("WTF");
		
		
//		String methodName = joinPoint.getSignature().getName();
//		List<Object> args = Arrays.asList(joinPoint.getArgs());
//		String asd = null ;
//		for(Object al: args) {
//			String sal = String.valueOf(al);
//			String[] listsal = sal.split(",");
//			for(String sa :listsal) {
//				Integer index  = sa.indexOf("stid=");
//				if(index != -1) {
//					System.out.println(index);
//					asd = sa.substring(index+5);
//				}
//				
//			}
//			System.out.println("al:"+al);
//		}
		
//		System.out.println("TureAnswer:"+asd);
		
//		System.out.println("AFTER Advice送出的訊息：方法 " + methodName + " 開始執行，傳入的參數為 " + args);
		System.out.println("進入到產品的AFTER AOP !!!!!!! ");

		
		
	}
}
