package login;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;
import server.Constants;

public class Login {
	String id = null, pw = null;
	String encpw;
	JDBC adminJDBC;
	boolean checkLogin;
	
	@SuppressWarnings("static-access")
	public void login(String[] array, HttpServletResponse resp) throws IOException{
		id = null; pw = null;
		Encryption enc = new Encryption();
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				id = array[j+2];
			}else if(array[j].equals("pw")){
				pw = array[j+2];
				encpw = enc.encryption(pw);
			}
		}
		
		adminJDBC = new JDBC();
		checkLogin = adminJDBC.loginDB(id, encpw);
		if(checkLogin){
			// 로그인 정보가 일치하는 경우
			// Ionic client에게 success라는 메시지를 보낸다.
			resp.getWriter().print(Constants.SUCCESS);
		}else{
			// 로그인 정보가 일치하지 않는 경우
			// Ionic client에게 fail이라는 메시지를 보낸다.
			resp.getWriter().print(Constants.FAIL);
		}
	}
}
