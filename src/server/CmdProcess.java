package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import img.ImageResize;
import img.ImageResizeFour;
import img.ImageResizeThree;
import img.ImageResizeTwo;


public class CmdProcess {	
	static String dburl=null;
	static JDBC adminJDBC;
	static boolean checkLogin; // �α��� ó�� �Ǿ����� üũ�ϴ� ����
	static boolean checkID; // �ߺ����̵� �ִ��� ó���ϴ� ����
	static String loginID; // �α��ε� ���̵� �ӽ÷� �����ϱ� ���� ����
	static String imgAddress; // �̹��� ��θ� �ӽ÷� �����صα� ���� ����
	
	static String[] album; // �����ͺ��̽����� ������ �ٹ� ����� ������ ���� ����
	static String[] fileName; // client�� ���� ���� ��θ� ������ ���� ����
	static int count = 0; // ������ �� �� ���Դ��� üũ�ϱ� ���� ����
	static String selectEmotion; // �����ͺ��̽����� ������ ���� ������ ����
	
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
			String id=null, pw=null;	
			
			for (int i = 0; i < array.length; i++){
				switch(array[i]){
				// � ��ư�� Ŭ���Ǿ������� ���� ó���Ǵ� �κ�
				case "check":
					// ���̵� �ߺ�Ȯ�� ��ư�� Ŭ���� ���
					ID_Check idCheck = new ID_Check();
					idCheck.loginCheck(array, resp);										
					break;
				
				case "register":
					// ȸ������ �ۼ��Ϸ� ��ư�� Ŭ���� ���					
					Register info = new Register();
					info.register(array);				
					
					break;
				
				case "login":					
					// �α��� ��ư�� Ŭ���� ���				
					Login access = new Login();
					access.login(array, resp);
					
					break;		
					
				case "imgSave":
					// �̹��� ���� ��ư�� Ŭ���� ���
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							loginID = array[j+2];
						}else if(array[j].equals("filename")){
							imgAddress = array[j+2];
						}else if(array[j].equals("faceRectangle\\")){
							emotion = new EstimationAnalysis(imgAddress);
							emotion.analysis(array);
						}
					}
					
					try {
						adminJDBC = new JDBC();
						adminJDBC.saveImg(loginID, imgAddress);
					} catch (SQLIntegrityConstraintViolationException e) {
						System.out.println("���Ἲ ���� ���ǿ� ����˴ϴ�.");
						//e.printStackTrace();
					}
					
					// ������ �����ͺ��̽��� ���� �Ϸ��ϰ� �̹��� �ռ� �۾��� �����Ѵ�.
					
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
					
				case "makealbum":
					// ���� ���� ��ư�� Ŭ���� ���
					fileName = new String[5];
					count = 0;
					for(int j = i; j <array.length; j++){
						if(array[j].equals("filename")){
							System.out.println(array[j+2]);
							fileName[count] = array[j+2];
							count++;
						}else if(array[j].equals("id")){
							loginID = array[j+2];
						}else if(array[j].equals("frame")){
							
						}
					} // End of for
					
					// ������ emotion�� �� ū ���� ���ؼ� �����´�.
					adminJDBC = new JDBC();
					try {
						selectEmotion = adminJDBC.getEmotion(fileName, count);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
					ImageResize imageResize = null;
					if(count+1 == 3){
						imageResize = new ImageResizeTwo();
						imageResize.resize(fileName, selectEmotion, loginID+"/");
					}else if(count+1 == 4){
						imageResize = new ImageResizeThree();
						imageResize.resize(fileName, selectEmotion, loginID+"/");
					}else if(count+1 == 5){
						imageResize = new ImageResizeFour();
						imageResize.resize(fileName, selectEmotion, loginID+"/");
					}
					
					
					
					break;
					
				default:
					
					break;
					
				}			
			}
		}		

		public static String getBody(HttpServletRequest request) throws IOException {
//			// �����ͺ��̽��� ���������� ����Ǿ����� Ȯ���ϴ� �κ�
//			adminJDBC = new JDBC();
//			adminJDBC.checkDB();

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
