package xun.test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import xun.controller.StoreIndexHomeController;

public class testLog4j2 {

	     static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
//	     static Logger logger = (Logger) LoggerFactory.getLogger(testLog4j2.class);
	 
	     public static void main(String[] args) {
	    	 
	         logger.trace("trace message");
	         logger.debug("debug message");
	         logger.info("info message");
	         logger.warn("warn message");
	         logger.error("error message");
	         logger.fatal("fatal message");
	         System.out.println("Hello World!");
	     }
}
