package _Init.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import xun.util.GlobalService;

public class InitialListener implements ServletContextListener{
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		context.setAttribute("SYSTEM", new GlobalService());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
