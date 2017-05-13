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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get!@!@#");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = getBody(req);
		String[] array = result.split("\"");
		dumpArray(array);
		System.out.println(result);
	}

	// 입력값에 따라 처리해줄 부분
	public static void dumpArray(String[] array) {
		for (int i = 0; i < array.length; i++)
			if(array[i].equals("id")){
				System.out.format("My ID is %s\n", array[i+2]);
			}else if(array[i].equals("pw")){
				System.out.println("pw 입력값");
				System.out.format("My PW is %s\n", array[i+2]);
			}
	}

	@SuppressWarnings("static-access")
	public static String getBody(HttpServletRequest request) throws IOException {
		// 데이터베이스가 연결되었는지 확인하는 부분
		JDBC test = new JDBC();
		test.startDB();

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
