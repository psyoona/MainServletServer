package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static String dburl=null;
	static JDBC adminJDBC;
	static boolean checkLogin; // �α��� ó�� �Ǿ����� üũ�ϴ� ����
	static boolean checkID; // �ߺ����̵� �ִ��� ó���ϴ� ����
	
	static EstimationAnalysis emotion; // ���� �м��ϱ� ���� Ŭ����
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get!@!@#");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("euc-kr");
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = getBody(req);
		String[] array = result.split("\"");
		dumpArray(array, resp);
		
		System.out.println(result);
	}

	// �Է°��� ���� ó������ �κ�
	@SuppressWarnings("static-access")
	public static void dumpArray(String[] array, HttpServletResponse resp) throws IOException {
		String id=null, pw=null, nickname=null;		
		
		for (int i = 0; i < array.length; i++){
			switch(array[i]){
			// � ��ư�� Ŭ���Ǿ������� ���� ó���Ǵ� �κ�
			case "check":
				// ���̵� �ߺ�Ȯ��				
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
				
//				System.out.println(id + " " + pw + " " + nickname);
				
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
					resp.getWriter().print("success");
				}else{
					// �α��� ������ ��ġ���� �ʴ� ���
					resp.getWriter().print("fail");
				}
				break;
				
			case "faceRectangle\\":
				// ���ν� ó���ϴ� �κ�
				emotion = new EstimationAnalysis();
				emotion.analysis(array);
				
//				for(int j = 0; j < array.length; j++){
//					if(array[j].equals("happiness\\")){
//						System.out.println(array[j+1]);
//					}
//				}
				break;
				
			case "showAlbum":
				break;
				
			case "fixPicture":
				break;
				
			default:
				
				break;
				
			}			
		}
	}

	@SuppressWarnings("static-access")
	public static String getBody(HttpServletRequest request) throws IOException {
		// �����ͺ��̽��� ����Ǿ����� Ȯ���ϴ� �κ�
		adminJDBC = new JDBC();
		adminJDBC.startDB();

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
