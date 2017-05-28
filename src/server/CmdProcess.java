package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CmdProcess {
	static String dburl = null;
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

	protected static void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String result = CmdProcess.getBody(req);
		String[] array = result.split("\"");
		System.out.println(result);
		
		
		cmdProcess(array, resp);

	}

	// 입력값에 따라 처리해줄 부분
	public static void cmdProcess(String[] array, HttpServletResponse resp) throws IOException {
		// 어떤 버튼이 클릭되었는지에 따라 처리하기 위한 for-switch문
		for (int i = 0; i < array.length; i++) {
			switch (array[i]) {
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
				ImgSave save = new ImgSave();
				save.imgSave(array);
				break;

			case "showAlbum":
				// 가운데 history 버튼이 클릭된 경우
//				ShowMergeAlbum album = new ShowMergeAlbum();
//				album.showMergeAlbum(array, resp);
				break;

			case "makeAlbum":
				// 사진 전송 후 자동으로 앨범 만들기 기능이 실행됨
				MakeAlbum mkAlbum = new MakeAlbum();
				mkAlbum.makeAlbum(array, i);
				break;

			default:
				break;
			}
		}
	}

	public static String getBody(HttpServletRequest request) throws IOException {
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
