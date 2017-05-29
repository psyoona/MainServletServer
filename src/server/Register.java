package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;

public class Register {
	String id=null, pw=null, nickname=null;	
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void register(String[] array, HttpServletResponse resp) throws IOException{		
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
		
		adminJDBC = new JDBC();
		// UserList �����ͺ��̽��� ���̵�, ��й�ȣ, �г����� ����Ѵ�.
		adminJDBC.registerDB(id, pw, nickname);
		// MergeCount �����ͺ��̽��� ���̵� ����ϰ� ī��Ʈ���� �ʱ�ȭ��Ų��.
		adminJDBC.MergeCount(id);
		
		// Ŭ���̾�Ʈ���� �Ϸ�Ǿ��ٰ� ������
		resp.getWriter().print(Constants.SUCCESS);
		
	}
}
