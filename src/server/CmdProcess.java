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
	static boolean checkLogin; // 로그인 처리 되었는지 체크하는 변수
	static boolean checkID; // 중복아이디가 있는지 처리하는 변수
	static String loginID; // 로그인된 아이디를 임시로 저장하기 위한 변수
	static String imgAddress; // 이미지 경로를 임시로 저장해두기 위한 변수
	
	static String[] album; // 데이터베이스에서 가져온 앨범 목록을 가지고 있을 변수
	static String[] fileName; // client가 보낸 파일 경로를 가지고 있을 변수
	static int count = 0; // 파일이 몇 개 들어왔는지 체크하기 위한 변수
	static String selectEmotion; // 데이터베이스에서 연산을 통해 결정된 감정
	
	static EstimationAnalysis emotion; // 감정 분석하기 위한 클래스
	
	protected static void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = CmdProcess.getBody(req);
		String[] array = result.split("\"");
		cmdProcess(array, resp);
		
		System.out.println(result);
	}
	
	// 입력값에 따라 처리해줄 부분
		@SuppressWarnings("static-access")
		public static void cmdProcess(String[] array, HttpServletResponse resp) throws IOException {
			String id=null, pw=null;	
			
			for (int i = 0; i < array.length; i++){
				switch(array[i]){
				// 어떤 버튼이 클릭되었는지에 따라 처리되는 부분
				case "check":
					// 아이디 중복확인 버튼이 클릭된 경우
					ID_Check idCheck = new ID_Check();
					idCheck.loginCheck(array, resp);										
					break;
				
				case "register":
					// 회원가입 작성완료 버튼이 클릭된 경우					
					Register info = new Register();
					info.register(array);				
					
					break;
				
				case "login":					
					// 로그인 버튼이 클릭된 경우				
					Login access = new Login();
					access.login(array, resp);
					
					break;		
					
				case "imgSave":
					// 이미지 전송 버튼이 클릭된 경우
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
						System.out.println("무결성 제약 조건에 위배됩니다.");
						//e.printStackTrace();
					}
					
					// 사진을 데이터베이스에 저장 완료하고 이미지 합성 작업을 시작한다.
					
					break;
					
				case "showAlbum":
					// 앨범 보기 버튼이 클릭된 경우
					for(int j = 0; j < array.length; j++){
						if(array[j].equals("id")){
							loginID = array[j+2];
						}
					}
					
					adminJDBC = new JDBC();
					album = adminJDBC.showAlbum(loginID);
					
					if(album != null){
						// 서버에 저장된 사진이 있는 경우
						// 우선 사진의 갯수를 먼저 전송한다.
						resp.getWriter().print(album.length);

						for(i = 0; i<album.length; i++){
							resp.getWriter().print(album[i]);	
						}
						
					}else{
						// 서버에 저장된 사진이 없는 경우
						resp.getWriter().print("notHave");
					}
					
					break;
					
				case "makealbum":
					// 사진 수정 버튼이 클릭된 경우
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
					
					// 사진의 emotion의 중 큰 값을 구해서 가져온다.
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
//			// 데이터베이스가 정상적으로 연결되었는지 확인하는 부분
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
