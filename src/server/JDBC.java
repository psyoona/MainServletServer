package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {
	static String dburl = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	
	static String db_id;
	static String db_pw;
	
	public JDBC(){
		db_id = "scott";
		db_pw = "mobile";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkDB(){
		// �����ͺ��̽��� ���������� ����Ǿ����� Ȯ���ϴ� �޼ҵ�		
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
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
	
	public static void registerDB(String id, String pw, String nickname){
		// ȸ������ ������ ����ϴ� �޼ҵ�
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
//			System.out.println(id + " " + pw + " " + nickname);
			pstmt = con.prepareStatement("INSERT INTO userList VALUES(?, ?, ?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nickname);
			pstmt.executeUpdate();
			
			System.out.println("���� �Ϸ�");
			
			// ȸ�������� �Ϸ�ʰ� ���ÿ� ������ �����Ѵ�.
			MakeDirectory.makeDirectory(id);
			
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
	

	public static boolean loginDB(String id, String pw){
		// �α����� �õ��� ���
		String id_db, pw_db;
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM userList");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				id_db = rs.getString("id");
				if(id_db.equals(id)){
					pw_db = rs.getString("pw");
					if(pw_db.equals(pw)){
						// ���̵�� �н����� ��� ��ġ�ϴ� ���
						System.out.println("�α��� ������ ��ġ�մϴ�!");
						return true;
					}
				}
			}			
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���Ÿ� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		return false;
	}

	public boolean checkID(String id) {
		// ���̵� �ߺ� üũ �޼ҵ�
		String id_db;
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM userList");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				id_db = rs.getString("id");
				if(id_db.equals(id)){
					// �����ͺ��̽��� ��ġ�ϴ� ���̵� �ִ� ���
					System.out.println("��ġ�ϴ� ���̵� ����");
					return true;
				}
			}			
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���Ÿ� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		return false;
	}
}
