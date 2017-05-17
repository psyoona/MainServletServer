package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmdProcess {
	static String dburl=null;
	static JDBC adminJDBC;
	static boolean checkLogin; // �α��� ó�� �Ǿ����� üũ�ϴ� ����
	static boolean checkID; // �ߺ����̵� �ִ��� ó���ϴ� ����
	static String loginID; // �α��ε� ���̵� �ӽ÷� �����ϱ� ���� ����
	static String imgAddress; // �̹��� ��θ� �ӽ÷� �����صα� ���� ����
	
	static String[] album; // �����ͺ��̽����� ������ �ٹ� ����� ������ ���� ����
	
	static EstimationAnalysis emotion; // ���� �м��ϱ� ���� Ŭ����
	
	protected static void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = CmdProcess.getBody(req);
		String[] array = result.split("\"");
		cmdProcess(array, resp);
		
		System.out.println(result);
	}
	
	// �Է°��� ���� ó������ �κ�
		@SuppressWarnings("static-access")
		public static void cmdProcess(String[] array, HttpServletResponse resp) throws IOException {
			String id=null, pw=null, nickname=null;		
			
			for (int i = 0; i < array.length; i++){
				switch(array[i]){
				// � ��ư�� Ŭ���Ǿ������� ���� ó���Ǵ� �κ�
				case "check":
					// ���̵� �ߺ�Ȯ�� ��ư�� Ŭ���� ���		
					id = null;
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							id = array[j+2];
						}
					}
					
					adminJDBC = new JDBC();
					checkID = adminJDBC.checkID(id);
					if(checkID){
						// �ߺ��� ���̵� �ִ� ���
						resp.getWriter().print("using");
					}else{
						// �ߺ��� ���̵� ���� ���
						resp.getWriter().print("notusing");
					}
					break;
				
				case "register":
					// ȸ������ �ۼ��Ϸ� ��ư�� Ŭ���� ���
					System.out.println("ȸ������ ��û�� ����");
					
					id = null; pw = null; nickname = null;
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							id = array[j+2];
						}else if(array[j].equals("pw")){
							pw = array[j+2];								
						}else if(array[j].equals("nickname")){
							nickname = array[j+2];
						}
					}
					
					adminJDBC = new JDBC();
					adminJDBC.registerDB(id, pw, nickname);
					break;
				
				case "login":					
					// �α��� ��ư�� Ŭ���� ���				
					id = null; pw = null; nickname = null;
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							id = array[j+2];
						}else if(array[j].equals("pw")){
							pw = array[j+2];
						}
					}
					
					adminJDBC = new JDBC();
					checkLogin = adminJDBC.loginDB(id, pw);
					if(checkLogin){
						// �α��� ������ ��ġ�ϴ� ���
						// Ionic client���� success��� �޽����� ������.
						resp.getWriter().print("success");
					}else{
						// �α��� ������ ��ġ���� �ʴ� ���
						// Ionic client���� fail�̶�� �޽����� ������.
						resp.getWriter().print("fail");
					}
					
					break;
					
//				case "faceRectangle\\":
//					// ���ν� ó���ϴ� �κ�
//					emotion = new EstimationAnalysis();
//					emotion.analysis(array);
//					
//					break;
					
				case "imgSave\\":
					// �̹��� ���� ��ư�� Ŭ���� ���
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							loginID = array[j+2];
						}else if(array[j].equals("filename")){
							imgAddress = array[j+2];
						}else if(array[j].equals("faceRectangle")){
							emotion = new EstimationAnalysis(imgAddress);
							emotion.analysis(array);
						}
					}
					
					adminJDBC = new JDBC();
					adminJDBC.saveImg(loginID, imgAddress);
					
					break;
					
				case "showAlbum":
					// �ٹ� ���� ��ư�� Ŭ���� ���
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							loginID = array[j+2];
						}
					}
					
					adminJDBC = new JDBC();
					album = adminJDBC.showAlbum(loginID);
					
					if(album != null){
						// ������ ����� ������ �ִ� ���
						// �켱 ������ ������ ���� �����Ѵ�.
						resp.getWriter().print(album.length);

						for(i = 0; i<album.length; i++){
							resp.getWriter().print(album[i]);	
						}
						
					}else{
						// ������ ����� ������ ���� ���
						resp.getWriter().print("notHave");
					}
					
					break;
					
				case "fixPicture":
					// ���� ���� ��ư�� Ŭ���� ���
					break;
					
				default:
					
					break;
					
				}			
			}
		}		

		@SuppressWarnings("static-access")
		public static String getBody(HttpServletRequest request) throws IOException {
			// �����ͺ��̽��� ���������� ����Ǿ����� Ȯ���ϴ� �κ�
			adminJDBC = new JDBC();
			adminJDBC.checkDB();

			String body = null;
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;

			try {
				InputStream inputStream = request.getInputStream();
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					char[] charBuffer = new char[128];
					int bytesRead = -1;
					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
						stringBuilder.append(charBuffer, 0, bytesRead);
					}
				}
			} catch (IOException ex) {
				throw ex;
				
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						throw ex;
					}
				}
			}

			body = stringBuilder.toString();

			return body;
		}
}
