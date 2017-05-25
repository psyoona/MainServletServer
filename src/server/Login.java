package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Login {
	String id = null, pw = null;
	JDBC adminJDBC;
	boolean checkLogin;
	
	@SuppressWarnings("static-access")
	public void login(String[] array, HttpServletResponse resp) throws IOException{
		id = null; pw = null;
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
			resp.getWriter().print(Constants.SUCCESS);
		}else{
			// �α��� ������ ��ġ���� �ʴ� ���
			// Ionic client���� fail�̶�� �޽����� ������.
			resp.getWriter().print(Constants.FAIL);
		}
	}
}
