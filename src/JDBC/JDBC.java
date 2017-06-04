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
	

	public static String getEmotion(String[] fileName, int count) throws InterruptedException{
		try{
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("SELECT * FROM emotion");
			rs = pstmt.executeQuery();			
			
			while(rs.next()){
				resultPath = rs.getString("imgPath");
				for(int i=0; i < count; i++){
					if(fileName[i].equals(resultPath)){
						tempEmotion = rs.getString("emotion");
						tempString = rs.getString("value");
						tempDouble = Double.valueOf(tempString);
						if(tempDouble > resultDouble){
							resultDouble = tempDouble;
							resultEmotion = tempEmotion;
							System.out.println("test " +resultEmotion);
						}						
					}
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
		
		return null;
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
			pstmt = con.prepareStatement("SELECT imgPath FROM imgMerge WHERE id='"+loginID+"'");
			rs = pstmt.executeQuery();
			
			imgPath = new String[count];
			int i = 0; //�ݺ� ����
			while(rs.next()){				
				imgPath[i] = rs.getString("imgPath"); 
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
			System.out.println("���޵� ��"+emotion);
			dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(dburl, db_id, db_pw);
			
			pstmt = con.prepareStatement("INSERT INTO emotion VALUES(?, ?, ?)");
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
