<%@page import="Store.photoBean"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*,java.sql.*,javax.sql.*,javax.naming.*,javax.servlet.ServletException,javax.servlet.ServletException,javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<%!	String[] sb; %>
<%
   File file ;
   int maxFileSize = 5000 * 1024;
   int maxMemSize = 5000 * 1024;
   //ServletContext context = pageContext.getServletContext();
   //String filePath = context.getInitParameter("file-upload");
   String filePath = "C:\\java\\JavaWebWorkSpace\\WebProject\\WebContent\\Images";
   System.out.println(filePath);

   // Verify the content type
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {
		
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      //File dir = new File("C:\\iii\\del!");
      //dir.mkdirs();
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      try{ 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);

         // Process the uploaded file items
         Iterator i = fileItems.iterator();

         out.println("<html>");
         out.println("<head>");
         out.println("<title>JSP File upload</title>");  
         out.println("</head>");
         out.println("<body>");
         while ( i.hasNext () ) 
         {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )	
            {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();

            System.out.println(fileName);
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
            file = new File( filePath + 
            fileName.substring( fileName.lastIndexOf("\\"))) ;
            }else{
            file = new File( filePath + 
            fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            fi.write( file ) ;
            out.println("Uploaded Filename: " + filePath + 
            fileName + "<br>");
            out.println(fileName);
            sb = fileName.split("\\\\");

            System.out.println(sb[sb.length-1]);
            File f = new File(filePath+"\\"+sb[sb.length-1]);
            System.out.println(filePath+"\\"+sb[sb.length-1]);

           	Object bb = session.getAttribute("banner");
           	
           	System.out.println(bb);        
            File f1 = new File("C:\\java\\JavaWebWorkSpace\\WebProject\\WebContent\\Images\\test\\banner.jpg");
//            f.renameTo(f1);
            }
         }
         out.println("<a href='Index1.jsp'>goback</a>");
         out.println("</body>");
         out.println("</html>");
         
 	    DataSource ds = null;
 	    InitialContext ctxt = null;
 	    Connection conn = null;
 	   try {
 		    
 	      ctxt = new InitialContext();

 	      ds = ( DataSource ) ctxt.lookup("java:comp/env/jdbc/EmployeeDB");

 	      conn = ds.getConnection();

 	     String sql = "update store set photourl=? where stname=?";
 	    PreparedStatement ps = conn.prepareStatement(sql);
 	    String url  = "Images/"+sb[sb.length-1];
	 	ps.setString(1, url);
	 	String a =(String) request.getSession().getAttribute("banner") ;
	 	ps.setString(2, a);
	 	ps.executeUpdate();
	 	ps.close();	
		conn.close();
 	    } catch (NamingException ne) {
 	      System.out.println("Naming Service Lookup Exception");  
 	    } catch (SQLException e) {
 	      System.out.println("Database Connection Error"); 
 	    } finally {
 	      try {
 	        if (conn != null) conn.close();
 	      } catch (Exception e) {
 	        System.out.println("Connection Pool Error!");
 	      }
 	    }
      }catch(Exception ex) {
         System.out.println(ex);
      }
   }else{
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      out.println("<p>No file uploaded</p>"); 
      out.println("</body>");
      out.println("</html>");
   }
%>

