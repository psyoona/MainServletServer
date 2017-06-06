package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import server.EstimationAnalysis.Emotion;
import server.MakeDirectory;

public class JDBC {
	static String dburl = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	
	static String db_id;
	static String db_pw;
	
	static String resultPath; // db에서 가져온 경로
	static String tempEmotion;
	static String resultEmotion; // 결정된 감정
	static String tempString;
	static String resultString;
	static double tempDouble;
	static double resultDouble=0;
	
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
			System.out.println("데이터 베이스 연결 이상 없음");
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
		
	public static boolean saveImg(String id, String imgAddr) throws SQLIntegrityConstraintViolationException{
		// 아이디와 이미지 경로를 데이터베이스에 저장하기 위한 메소드
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO imgAddr VALUES(?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, imgAddr);
			pstmt.executeUpdate();
			
			System.out.println("아이디와 사진 경로 저장 완료");	
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
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
			
			System.out.println("회원 정보 저장 완료");
			
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
						System.out.println(id_db+"");
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
	

	// 파일이 한 개일 때 
	public static String getEmotion(String fileName1) throws InterruptedException{
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM emotion");
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				resultPath = rs.getString("path");
				
				if(fileName1.equals(resultPath)){
					resultEmotion = rs.getString("emotion");					
				}				
			} // End of While
			
			return resultEmotion;
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// 파일이 두 개일 때 가장 높은 emotion String을 return 한다.
	// 기본은 neutral이다.
	public static String getEmotion(String fileName1, String fileName2) throws InterruptedException{
		String firstEmotion = null;
		String tempString1;
		Double firstValue = 0.0;
		
		String secondEmotion = null;
		String tempString2;
		Double secondValue = 0.0;
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM emotion");
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				resultPath = rs.getString("path");
				
				if(fileName1.equals(resultPath)){
					firstEmotion = rs.getString("emotion");
					tempString1 = rs.getString("value");
					firstValue = Double.valueOf(tempString1);
				}
				if(fileName2.equals(resultPath)){
					secondEmotion = rs.getString("emotion");
					tempString2 = rs.getString("value");
					secondValue = Double.valueOf(tempString2);
				}
			} // End of While
			
			
			if(firstValue == secondValue){
				resultEmotion = "neutral";
			}else if(firstValue > secondValue){
				resultEmotion = firstEmotion;
			}else if(firstValue < secondValue){
				resultEmotion = secondEmotion;
			}else{
				resultEmotion = "neutral";
			}
			return resultEmotion;
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// 파일이 세 개일 때 가장 높은 emotion String을 return 한다.
	// 기본은 neutral이다.
	public static String getEmotion(String fileName1, String fileName2, String fileName3) throws InterruptedException{
		String firstEmotion = null;
		String tempString1;
		Double firstValue = 0.0;
		
		String secondEmotion = null;
		String tempString2;
		Double secondValue = 0.0;
		
		String thirdEmotion = null;
		String tempString3;
		Double thirdValue = 0.0;
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM emotion");
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				resultPath = rs.getString("path");
				if(fileName1.equals(resultPath)){
					firstEmotion = rs.getString("emotion");
					tempString1 = rs.getString("value");
					firstValue = Double.valueOf(tempString1);
				}
				if(fileName2.equals(resultPath)){
					secondEmotion = rs.getString("emotion");
					tempString2 = rs.getString("value");
					secondValue = Double.valueOf(tempString2);
				}
				if(fileName3.equals(resultPath)){
					thirdEmotion = rs.getString("emotion");
					tempString3 = rs.getString("value");
					thirdValue = Double.valueOf(tempString3);
				}
			} // End of While
			if(firstValue == secondValue){
				resultEmotion = "neutral";
			}else if(firstValue > secondValue){
				if(firstValue > thirdValue){
					resultEmotion = firstEmotion;
				}else{
					resultEmotion = thirdEmotion;
				}
			}else if(firstValue < secondValue){
				if(secondValue > thirdValue){
					resultEmotion = secondEmotion;
				}else{
					resultEmotion = thirdEmotion;
				}
			}else{
				resultEmotion = "neutral";
			}
			return resultEmotion;
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// 파일명이 네 개일 때 가장 높은 emotion String을 return 한다.
	// 기본은 neutral이다.
	public String getEmotion(String fileName1, String fileName2, String fileName3, String fileName4) {
		String firstEmotion = null;
		String tempString1;
		Double firstValue = 0.0;
		
		String secondEmotion = null;
		String tempString2;
		Double secondValue = 0.0;
		
		String thirdEmotion = null;
		String tempString3;
		Double thirdValue = 0.0;
		
		String forthEmotion = null;
		String tempString4;
		Double forthValue = 0.0;
		
		String tempEmotion1 = "neutral";
		String tempEmotion2 = "neutral";
		Double tempValue1 = 0.0;
		Double tempValue2 = 0.0;
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM emotion");
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				resultPath = rs.getString("path");
				if(fileName1.equals(resultPath)){
					firstEmotion = rs.getString("emotion");
					tempString1 = rs.getString("value");
					firstValue = Double.valueOf(tempString1);
				}
				if(fileName2.equals(resultPath)){
					secondEmotion = rs.getString("emotion");
					tempString2 = rs.getString("value");
					secondValue = Double.valueOf(tempString2);
				}
				if(fileName3.equals(resultPath)){
					thirdEmotion = rs.getString("emotion");
					tempString3 = rs.getString("value");
					thirdValue = Double.valueOf(tempString3);
				}
				if(fileName4.equals(resultPath)){
					thirdEmotion = rs.getString("emotion");
					tempString4 = rs.getString("value");
					thirdValue = Double.valueOf(tempString4);
				}
			} // End of While
			
			if(firstValue > secondValue){
				tempValue1 = firstValue;
				tempEmotion1 = firstEmotion;
			}else{
				tempValue1 = secondValue;
				tempEmotion1 = secondEmotion;
			}
			
			if(thirdValue > forthValue){
				tempValue2 = thirdValue;
				tempEmotion2 = thirdEmotion;
			}else{
				tempValue2 = forthValue;
				tempEmotion2 = forthEmotion;
			}
			
			if(tempValue1 > tempValue2){
				resultEmotion = tempEmotion1;
			}else{
				resultEmotion = tempEmotion2;
			}
			
			return resultEmotion;
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
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
	
	public String[] showMergeAlbum(String loginID) {
		// 사진 경로를 가져와서 유저에게 전달하는 메소드
		int count = 0; // 테이블의 행의 갯수를 저장하기 위한 변수 
		String[] imgPath = null;
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM imgMerge");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt("COUNT(*)");
			}
			System.out.println(count);
			pstmt = con.prepareStatement("SELECT path FROM imgMerge WHERE id='"+loginID+"'");
			rs = pstmt.executeQuery();
			
			imgPath = new String[count];
			int i = 0; //반복 제어
			while(rs.next()){				
				imgPath[i] = rs.getString("path"); 
				i++;
			}
			
			for(int j=0; j < i; j++){
				System.out.println(imgPath[j]);
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
	

	public void saveEmotion(String imgPath, Emotion firstEmotion, Double value) {
		try{
			String emotion = String.valueOf(firstEmotion);
			//System.out.println("전달된 값"+emotion);
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO emotion VALUES(?, ?, ?)");
			System.out.println("파일이름: "+imgPath);
			pstmt.setString(1, imgPath);
			pstmt.setString(2, emotion);
			pstmt.setDouble(3, value);
			pstmt.executeUpdate();
			
			System.out.println("save Emotion: 저장 완료");
			
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

	/*
	 * 아이디가 생성됨과 동시에 MergeCount 테이블에 아이디를 등록하고
	 * count값을 0으로 초기화 시킨다. 
	 */
	public void MergeCount(String id) {
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
//			System.out.println("Save Emotion Function");
			
			pstmt = con.prepareStatement("INSERT INTO mergeCount VALUES(?, ?)");
			pstmt.setString(1, id);
			pstmt.setInt(2, 0);
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

	public void addCount(String id) {
		int count=0;
		// MergeCount에 있는 count 값을 1 증가시킨다.
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM mergeCount WHERE id='"+id+"'");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				count = rs.getInt("count");
				System.out.println("Here?");
				
			}
			System.out.println(count);
			count++;
			System.out.println(count);
			
			//카운트를 증가시켰기 때문에 그대로 다시 저장만 해주면 됨.			
			
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

	public void saveMerge(String loginID, String path, String emotion) {
		// 아이디와 이미지 경로를 데이터베이스에 저장하기 위한 메소드
		try{
			System.out.println("SaveMerge 내부 시작");
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO imgMerge VALUES(?,?, ?)");
			pstmt.setString(1, loginID);
			pstmt.setString(2, path);
			pstmt.setString(3, emotion);
			pstmt.executeUpdate();
			
//			System.out.println("SaveMerge 내부 끝");			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}		
	}

	public void originalImg(String loginID, String path) {
		try{
			System.out.println("filterImg 내부 시작");
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO filterImg VALUES(?,?)");
			pstmt.setString(1, loginID);
			pstmt.setString(2, path);
			pstmt.executeUpdate();
			
		} catch(IllegalArgumentException e){
			System.out.println("입력 형태를 확인하세요");
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
	}
	
}
