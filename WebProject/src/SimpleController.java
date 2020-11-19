

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Store.StoreDB;

/**
 * Servlet implementation class SimpleController
 */
@WebServlet("/SimpleController")
public class SimpleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	DataSource ds;
//    /**
//     * @see HttpServlet#HttpServlet()
//     */

    public SimpleController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
//		-------
		
		try {
		      // Create a JNDI Initial context to be able to lookup the DataSource
		      InitialContext ctx = new InitialContext();
		      // Lookup the DataSource, which will be backed by a pool
		      //   that the application server provides.
		      ds = (DataSource)ctx.lookup("java:comp/env/jdbc/EmployeeDB");
		      if (ds == null)
		         throw new ServletException("Unknown DataSource 'jdbc/EmployeeDB'");
		  } catch (NamingException ex) {
		      ex.printStackTrace();
		  }
		
		  Connection conn = null;
	      Statement  stmt = null;
	      
	      List <String> stnameList =  new  ArrayList <String> () ;
	      List <String> sclassList = new  ArrayList <String> ();
	      List <String> saddressList = new  ArrayList <String> ();
	      
	      try {
	         	 
	         // Get a connection from the pool
	         conn = ds.getConnection();
	 
	         // Normal JBDC programming hereafter. Close the Connection to return it to the pool
	         stmt = conn.createStatement();
	         ResultSet rset = stmt.executeQuery("SELECT stname, sclass, saddress FROM Store ");
	        
	         while(rset.next()) {        	 
	        	 stnameList.add(rset.getString("stname"));
	        	 sclassList.add(rset.getString("sclass"));
	        	 saddressList.add(rset.getString("saddress"));
	         }
	         
	         StoreDB.setStname((String[]) stnameList.toArray(new String[0]));
	         StoreDB.setSclass((String[]) sclassList.toArray(new String[0]));
	         StoreDB.setSaddress((String[]) saddressList.toArray(new String[0]));

	      } catch (SQLException ex) {
	         ex.printStackTrace();
	      } finally {
	   
	         try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();  // return to pool
	         } catch (SQLException ex) {
	             ex.printStackTrace();
	         }
	      }
	      
//	      ------------------------
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); 
		
		PrintWriter out = response.getWriter();

			
//		for(int i =0 ; i < StoreDB.size() ; i++) {
//			out.println(StoreDB.getStname(i));
//			out.println(StoreDB.getSaddress(i));
//			out.println(StoreDB.getSclass(i));
//		}
		
		  String nextPage = "/SimpleStore.jsp";
	      ServletContext servletContext = getServletContext();
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
	      requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
