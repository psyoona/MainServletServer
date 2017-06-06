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
	
	static String resultPath; // db���� ������ ���
	static String tempEmotion;
	static String resultEmotion; // ������ ����
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
		// �����ͺ��̽��� ���������� ����Ǿ����� Ȯ���ϴ� �޼ҵ�		
		
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			System.out.println("������ ���̽� ���� �̻� ����");
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}
		
	public static boolean saveImg(String id, String imgAddr) throws SQLIntegrityConstraintViolationException{
		// ���̵�� �̹��� ��θ� �����ͺ��̽��� �����ϱ� ���� �޼ҵ�
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO imgAddr VALUES(?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, imgAddr);
			pstmt.executeUpdate();
			
			System.out.println("���̵�� ���� ��� ���� �Ϸ�");	
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
			
			System.out.println("ȸ�� ���� ���� �Ϸ�");
			
			// ȸ�������� �Ϸ�ʰ� ���ÿ� ������ �����Ѵ�.
			MakeDirectory.makeDirectory(id);
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
						System.out.println(id_db+"");
						return true;
					}
				}
			}		
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		return false;
	}
	

	// ������ �� ���� �� 
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
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// ������ �� ���� �� ���� ���� emotion String�� return �Ѵ�.
	// �⺻�� neutral�̴�.
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
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// ������ �� ���� �� ���� ���� emotion String�� return �Ѵ�.
	// �⺻�� neutral�̴�.
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
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
		
		return "neutral";
	}
	
	// ���ϸ��� �� ���� �� ���� ���� emotion String�� return �Ѵ�.
	// �⺻�� neutral�̴�.
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
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
		// ���� ��θ� �����ͼ� �������� �����ϴ� �޼ҵ�
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
					// �����ͺ��̽��� ��ġ�ϴ� ���̵� �ִ� ���
					imgPath[0] = rs.getString("addr");
				}
			}		
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
		// ���� ��θ� �����ͼ� �������� �����ϴ� �޼ҵ�
		int count = 0; // ���̺��� ���� ������ �����ϱ� ���� ���� 
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
			int i = 0; //�ݺ� ����
			while(rs.next()){				
				imgPath[i] = rs.getString("path"); 
				i++;
			}
			
			for(int j=0; j < i; j++){
				System.out.println(imgPath[j]);
			}
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
			//System.out.println("���޵� ��"+emotion);
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO emotion VALUES(?, ?, ?)");
			System.out.println("�����̸�: "+imgPath);
			pstmt.setString(1, imgPath);
			pstmt.setString(2, emotion);
			pstmt.setDouble(3, value);
			pstmt.executeUpdate();
			
			System.out.println("save Emotion: ���� �Ϸ�");
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}

	/*
	 * ���̵� �����ʰ� ���ÿ� MergeCount ���̺� ���̵� ����ϰ�
	 * count���� 0���� �ʱ�ȭ ��Ų��. 
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
			
			System.out.println("���� �Ϸ�");
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
		// MergeCount�� �ִ� count ���� 1 ������Ų��.
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
			
			//ī��Ʈ�� �������ױ� ������ �״�� �ٽ� ���常 ���ָ� ��.			
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(con != null){try{con.close();}catch(Exception e){}}
			if(pstmt != null){try{pstmt.close();}catch(Exception e){}}
			if(rs != null){try{rs.close();}catch(Exception e){}}
		}
	}

	public void saveMerge(String loginID, String path, String emotion) {
		// ���̵�� �̹��� ��θ� �����ͺ��̽��� �����ϱ� ���� �޼ҵ�
		try{
			System.out.println("SaveMerge ���� ����");
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO imgMerge VALUES(?,?, ?)");
			pstmt.setString(1, loginID);
			pstmt.setString(2, path);
			pstmt.setString(3, emotion);
			pstmt.executeUpdate();
			
//			System.out.println("SaveMerge ���� ��");			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
			System.out.println("filterImg ���� ����");
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO filterImg VALUES(?,?)");
			pstmt.setString(1, loginID);
			pstmt.setString(2, path);
			pstmt.executeUpdate();
			
		} catch(IllegalArgumentException e){
			System.out.println("�Է� ���¸� Ȯ���ϼ���");
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
