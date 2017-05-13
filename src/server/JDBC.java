package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {
	public JDBC(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startDB(){
		String dburl = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, "bookmaster", "book");
			System.out.println("������ ���̽� ���� �Ϸ�");
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���Ÿ� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}
	
}
