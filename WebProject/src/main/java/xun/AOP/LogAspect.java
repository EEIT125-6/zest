package xun.AOP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.logging.log4j.LogManager;
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
import xun.controller.StoreR_Controller;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.service.StoreService;

@Aspect
@Component
public class LogAspect {

	@Autowired
	StoreService ss;
	
	@Autowired
	ProductService ps;
	
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
		
		org.apache.logging.log4j.Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

		logger.info("infoMessage");
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
		
		boolean nimasb=false;
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("*****************");
		System.out.println(args);
		System.out.println("*****************");
		String asd = null ;
		for(Object al: args) {
			String sal = String.valueOf(al);
			String[] listsal = sal.split(",");
			for(String sa :listsal) {
//				sa.replaceAll(regex, replacement)
				String sas = "(.*)stid=(.*)";
				System.out.println("---");
				System.out.println(sa);
				System.out.println("+++");
				System.out.println(sa.matches(sas));
				
				if(sa.matches(sas)) {
					Integer index  = sa.indexOf("stid=");
					if(index != -1) {
						System.out.println(index);
						asd = sa.substring(index+5);
						nimasb=true;
						break;
					}
				}else {
					System.out.println("並不包含");
//					return;
				}
				
			}
			System.out.println("al:"+al);
		}
		if(nimasb == false || asd.isEmpty()) {
			return;
		}
		System.out.println("TureAnswer:"+asd);
		Integer stid = Integer.valueOf(asd);
		StoreBean sb = ss.get(stid);
		
		List<Integer> productsprice = new ArrayList<Integer>() ;
		for (ProductInfoBean pi : ps.getStoreProduct(sb)) {
			Integer ss =  pi.getProduct_price();
			productsprice.add(ss);
		}
		
		Collections.sort(productsprice);
		Integer storeprice=null;
		if(productsprice.size()%2 !=0) {
			storeprice=productsprice.get((productsprice.size()+1)/2-1);
			System.out.println(storeprice);
			System.out.println("+++++++++++"+productsprice);
		}else if(productsprice.size()==0){
			storeprice=0;
		}else {
			storeprice=productsprice.get((productsprice.size()/2));
			
			System.out.println(storeprice);
			System.out.println("-----------"+productsprice);
		}
		if (storeprice < 150) {
			storeprice = 1;
		}else if(storeprice < 300) {
			storeprice = 2;
		}else if(storeprice < 450) {
			storeprice = 3;
		}else if(storeprice < 600) {
			storeprice = 4;
		}else {
			storeprice = 5;
		}
		
		Integer Result  = ss.setStorePrice(storeprice, sb.getId());
		System.out.println("成功修改STORE_PRICE是1:"+Result);
//		System.out.println("AFTER Advice送出的訊息：方法 " + methodName + " 開始執行，傳入的參數為 " + args);
		System.out.println("進入到產品的AFTER AOP !!!!!!! ");

		
		
	}
}