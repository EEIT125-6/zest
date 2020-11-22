package orange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommentDAO {

	private Connection conn;
	
	//建構子
	public CommentDAO(Connection conn) {
		this.conn = conn;
	}
	
	public CommentBean selectComment(String name) {
		CommentBean commentBean = new CommentBean();
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from dbo.Board where name ='"+name+"'");	){
			
			while(rs.next()) {
				commentBean.setName(rs.getString("NAME"));
				commentBean.setStars(rs.getInt("STARS"));
				commentBean.setDate(rs.getDate("DATE"));
				commentBean.setContext(rs.getString("CONTEXT"));
				commentBean.setPhoto(rs.getString("PHOTO"));
			}
			System.out.println("DAO"+commentBean.getName());
			return commentBean;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commentBean;

	}
	
	public boolean updateComment(CommentBean commentBean) {
		try {
			PreparedStatement pstmt
			 = conn.prepareStatement(" UPDATE dbo.Board SET stars=?,context=?,photo=?,date=? where name=?");
			
			pstmt.setString(5, commentBean.getName());
			pstmt.setInt(1, commentBean.getStars());
//			java.util.Date date = new Date(); 
			java.sql.Date sqlDate = new java.sql.Date(commentBean.getDate().getTime());
			pstmt.setDate(4, sqlDate);
			pstmt.setString(2, commentBean.getContext());
			pstmt.setString(3, commentBean.getPhoto());
			int update = pstmt.executeUpdate();
			if(update>=1) {
				System.out.println("update success");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteComment(String name) {
		
		try (PreparedStatement pstmt
				 = conn.prepareStatement("DELETE FROM dbo.Board WHERE Name=?");){
		
			pstmt.setString(1, name);
			
			int update = pstmt.executeUpdate();
			if(update>=1)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return true;
	}
	
	public boolean insertComment(CommentBean comment){
		
		try (PreparedStatement pstmt
				 = conn.prepareStatement(" INSERT INTO dbo.Board VALUES (?,?,?,?,?)")){
			
			System.out.println("test");
			pstmt.setString(1, comment.getName());
			pstmt.setInt(2, comment.getStars());
//			java.util.Date date = new Date(); 
			java.sql.Date sqlDate = new java.sql.Date(comment.getDate().getTime());
			pstmt.setDate(3, sqlDate);
			pstmt.setString(4, comment.getContext());
			pstmt.setString(5, comment.getPhoto());
			int update = pstmt.executeUpdate();
			
			if(update>=1)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
