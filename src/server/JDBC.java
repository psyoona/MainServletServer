package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.EstimationAnalysis.Emotion;

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
		// 데이터베이스가 정상적으로 연결되었는지 확인하는 메소드		
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			System.out.println("데이터 베이스 연결 완료");
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}
	
	public static boolean saveImg(String id, String imgAddr){
		// 아이디와 이미지 경로를 데이터베이스에 저장하기 위한 메소드
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO imgAddr VALUES(?,?)");
			pstmt.setString(1,  id);
			pstmt.setString(2, imgAddr);
			pstmt.executeUpdate();
			
			System.out.println("아이디와 사진 경로 저장 완료");			
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}		
		return false;
	}
	
	public static void registerDB(String id, String pw, String nickname){
		// 회원가입 정보를 등록하는 메소드
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
//			System.out.println(id + " " + pw + " " + nickname);
			pstmt = con.prepareStatement("INSERT INTO userList VALUES(?, ?, ?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nickname);
			pstmt.executeUpdate();
			
			System.out.println("저장 완료");
			
			// 회원가입이 완료됨과 동시에 폴더를 생성한다.
			MakeDirectory.makeDirectory(id);
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}	
	

	public static boolean loginDB(String id, String pw){
		// 로그인이 시도된 경우
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
						// 아이디와 패스워드 모두 일치하는 경우
						System.out.println("로그인 정보가 일치합니다!");
						return true;
					}
				}
			}			
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
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
		// 아이디 중복 체크 메소드
		String id_db;
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM userList");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				id_db = rs.getString("id");
				if(id_db.equals(id)){
					// 데이터베이스에 일치하는 아이디가 있는 경우
					System.out.println("일치하는 아이디 있음");
					return true;
				}
			}			
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		return false;
	}

	@SuppressWarnings("null")
	public String[] showAlbum(String loginID) {
		// 사진 경로를 가져와서 유저에게 전달하는 메소드
		String id_db;
		String[] imgPath = null;
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM imgAddr");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				id_db = rs.getString("id");
				if(id_db.equals(loginID)){
					// 데이터베이스에 일치하는 아이디가 있는 경우
					imgPath[0] = rs.getString("addr");
				}
			}			
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		return imgPath;
		
	}

	public void saveEmotion(String imgPath, Emotion firstEmotion) {
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO emotion VALUES(?, ?)");
			pstmt.setString(1, imgPath);
			pstmt.setString(2, String.valueOf(firstEmotion));
			pstmt.executeUpdate();
			
			System.out.println("저장 완료");
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}
}
