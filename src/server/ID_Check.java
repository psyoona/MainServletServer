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
			// �ߺ��� ���̵� �ִ� ���
			resp.getWriter().print("using");
		}else{
			// �ߺ��� ���̵� ���� ���
			resp.getWriter().print("notusing");
		}
	}
	
}
