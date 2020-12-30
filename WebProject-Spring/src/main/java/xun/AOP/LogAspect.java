package xun.AOP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import xun.controller.ProductCUD_Controller;
import xun.controller.StoreR_Controller;
import xun.service.StoreService;

@Aspect
@Component

public class LogAspect {

	@Autowired
	StoreService ss;
	

	@Autowired
	HttpServletRequest req;
	
	@Pointcut("execution(* xun.controller.*.*(..))")
	public void pointcut() {
		
	}
	
	
	@Before("pointcut()")
	public void before(JoinPoint joinPoint
		
			) {
		Logger logger = Logger.getLogger(StoreR_Controller.class);
//		Logger logger = LoggerFactory.getLogger(getClass());
//		logger.info("star");
//		System.out.println("before!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println(joinPoint.getSignature().getName());
//		System.out.println("END!!!!!!!!!!!!!!!!!!");
		String sa = ss.get(1).getStname();
//		System.out.println("sasasasasasasasasasasasa"+sa);
		logger.info("print STAR BEFORE!!!");
//		System.out.println("BEFORE!");
		logger.info("SS");
		File testdir = new File("C:/bookestore!");
		boolean ee = testdir.mkdir();
//		System.out.println(ee);
		
		
	}

	@After("pointcut()")
	public void afterLog(JoinPoint joinPoint) {
		

		String remoteAddr = req.getRemoteAddr();
//		System.out.println(remoteAddr);
		
		System.out.println("資料寫進LOG");
		File logpath = new File("C://WebProjectLog");
		logpath.mkdir();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
        Date date = new Date();// 获取当前时间 
        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        File logfile = new File("C://WebProjectLog//"+sdf.format(date)+".txt");
        if(!logfile.exists()) {
        	try {
				logfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("methodName:"+methodName);
//		System.out.println("args:"+args);
//		-----------------------------------------
        StringBuffer buf = new StringBuffer();
    	try (
    			FileInputStream fis	= new FileInputStream(logfile);
    			InputStreamReader isr =  new InputStreamReader(fis,"UTF-8");
    			BufferedReader br = new BufferedReader(isr);
    			){
    		String line = "";
    		while ((line = br.readLine()) != null) { 
//    			System.out.println(line); 
    			buf.append(line);
    			buf.append(System.getProperty("line.separator"));
    			} 				
    		System.out.println("讀取完畢");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	try (
    			FileOutputStream fos = new FileOutputStream(logfile);
    			PrintWriter pw = new PrintWriter(fos);        			
    			){
//    		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
    		sdf.applyPattern(" HH:mm:ss a");
    		String newcontent = 
    				"執行時間 : "+  sdf.format(date)
    				+"  methodName:"+methodName
//    				+"  args:"+args
    				;
    		buf.append(newcontent);
//    		System.out.println(buf.toString());
    		pw.write(buf.toString());
    		pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		-----------------------------------------
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
