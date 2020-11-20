import java.sql.*;

public class BookingDAO {
	
	 private Connection conn;

	 
	  public BookingDAO(Connection conn) {
			this.conn = conn;
	  }
	  //新增insert
	  public boolean insertBooking(booking.bean.BookingBean bookingData) {
		    try {
		      String sqlString = "insert into Booking values('"
			                  	   	+ bookingData.getBookingNo()+"','"
				                    + bookingData.getBookingdate()+"','"
		                            + bookingData.getTime()+"',"
		                            + bookingData.getNumber()+",'"
		                            + bookingData.getRestaurant()+"','" 
		                            + bookingData.getName()+"','"
		                            + bookingData.getPhone()+"','"
		                            + bookingData.getMail()+"','"
		                            + bookingData.getPurpose()+"','"
		                            + bookingData.getNeeds()+"','')";
		                           
		      Statement stmt = conn.createStatement();
		      System.out.println(sqlString);
			    int updatecount = stmt.executeUpdate(sqlString);
		      stmt.close();
		      if (updatecount >= 1) return true;
		      else                  return false;
			  } catch (Exception e) {
			    System.err.println("新增訂單時發生錯誤:" + e);
				  return false;
		    }
		  }
	  //刪除delete
	  public boolean cancelBooking(String phone) {
		    try {
		      String sqlString = "delete from Booking Where phone = " + "'"+phone+"'";      
		      System.out.println(sqlString);
		      Statement stmt = conn.createStatement();
			  int updatecount = stmt.executeUpdate(sqlString);
		      stmt.close();
		      if (updatecount >= 1) return true;
		      else                  return false;
			  } catch (Exception e) {
			    System.err.println("刪除訂單時發生錯誤:" + e);
				  return false;
		    }
		  }

	  //查詢query
//	  public List<BookingBean> findBooking(String phone) {
//		  List<BookingBean> aa = new ArrayList<>();
//		    try (Statement stmt = conn.createStatement()){
//		      
//		      String sqlString = "SELECT *" + " FROM booking WHERE phone = '" + phone+"'";
//		      System.out.println(sqlString);
//		      
//			  ResultSet rs = stmt.executeQuery(sqlString);
//
//		      while (rs.next()) {
//		    	BookingBean data= new BookingBean();
//		    	data.setBookingNo(rs.getString("bookingNo"));
//		    	data.setBookingdate(rs.getString("bookingdate"));
//		    	data.setTime(rs.getString("time"));
//		    	data.setNumber(rs.getInt("number"));
//		    	data.setRestaurant(rs.getString("restaurant"));
//		    	data.setName(rs.getString("name"));
//		    	aa.add(data);
//		    	System.out.println(data.getBookingNo());
//		    	System.out.println(data.getBookingdate());
//		    	System.out.println(data.getTime());
//		    	System.out.println(data.getNumber());
//		    	System.out.println(data.getRestaurant());
//		    	System.out.println(data.getName());
//			  }
//			  rs.close();
//			  return aa;
//			  
//		    } catch (Exception e) {
//			    System.err.println("查詢訂單時發生錯誤:" + e);
//			    return null;
//		    }
//		}
	  
	//查詢query
	  public booking.bean.BookingBean findBooking(String phone) {
		    try (Statement stmt = conn.createStatement()){
		      
		      String sqlString = "SELECT *" +
			       			  " FROM booking WHERE phone = '" + phone+"'";
		      System.out.println(sqlString);
		      
		      booking.bean.BookingBean data = new booking.bean.BookingBean();
			  ResultSet rs = stmt.executeQuery(sqlString);

		      while (rs.next()) {
		    	data.setBookingNo(rs.getString("bookingNo"));
		    	data.setBookingdate(rs.getString("bookingdate"));
		    	data.setTime(rs.getString("time"));
		    	data.setNumber(rs.getInt("number"));
		    	data.setRestaurant(rs.getString("restaurant"));
		    	data.setName(rs.getString("name"));
		    	data.setPhone(rs.getString("phone"));
		    	data.setMail(rs.getString("email"));
		    	data.setNeeds(rs.getString("needs"));
		    	System.out.println(data.getBookingNo());
		    	System.out.println(data.getRestaurant());
		    	System.out.println(data.getBookingdate());
		    	System.out.println(data.getTime());
		    	System.out.println(data.getNumber());
		    	System.out.println(data.getName());
			  }
			  rs.close();
			  return data;
			  
		    } catch (Exception e) {
			    System.err.println("查詢訂單時發生錯誤:" + e);
		    }
			return null;
		}
	  
	  
	  
//		更新update
	  public boolean updateBooking(String bookingdate,String time,String number,String name,String phone,String mail,String needs) {
		    try {
		    	
		      String sqlString = "update Booking SET bookingdate = '" + bookingdate+"'"+
		    		  							", time = '" + time+"'"+
		    		  							", number = '" + number+"'"+
		    		  							", name ='" + name+"'"+
		    		  							", phone ='" + phone+"'"+
		    		  							", email ='" + mail+"'"+
		    		  							", needs = '" + needs+"'"+
		    		  							" FROM booking WHERE phone = '" + phone+"'";
		                           
		      Statement stmt = conn.createStatement();
		      System.out.println(sqlString);
			  int updatecount = stmt.executeUpdate(sqlString);
		      stmt.close();
		      if (updatecount >= 1) return true;
		      else                  return false;
			  } catch (Exception e) {
			    System.err.println("修改訂單時發生錯誤:" + e);
				  return false;
		    }
		  }

}
