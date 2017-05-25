package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ID_Check {
	String id=null, pw=null, nickname=null;
	JDBC adminJDBC;
	boolean checkID;
	
	public ID_Check(){
		
	}
	
	public void loginCheck(String[] array, HttpServletResponse resp) throws IOException{
		id = null;
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				id = array[j+2];
			}
		}
		
		adminJDBC = new JDBC();
		checkID = adminJDBC.checkID(id);
		if(checkID){
			// 중복된 아이디가 있는 경우
			resp.getWriter().print("using");
		}else{
			// 중복된 아이디가 없는 경우
			resp.getWriter().print("notusing");
		}
	}
	
}
