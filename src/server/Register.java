package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;

public class Register {
	String id=null, pw=null, nickname=null, encpw=null;	
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void register(String[] array, HttpServletResponse resp) throws IOException{
		Encryption enc = new Encryption();
		id = null; pw = null; nickname = null;
		
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				id = array[j+2];
			}else if(array[j].equals("pw")){
				pw = array[j+2];
				encpw = enc.encryption(pw);
			}else if(array[j].equals("nickname")){
				nickname = array[j+2];
			}
		}
		
		adminJDBC = new JDBC();
		// UserList 데이터베이스에 아이디, 비밀번호, 닉네임을 등록한다.
		adminJDBC.registerDB(id, encpw, nickname);
		// MergeCount 데이터베이스에 아이디를 등록하고 카운트값을 초기화시킨다.
		adminJDBC.MergeCount(id);
		
		// 클라이언트에게 완료되었다고 응답함
		resp.getWriter().print(Constants.SUCCESS);
		
	}
}
