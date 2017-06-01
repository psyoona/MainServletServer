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
		// UserList �����ͺ��̽��� ���̵�, ��й�ȣ, �г����� ����Ѵ�.
		adminJDBC.registerDB(id, encpw, nickname);
		// MergeCount �����ͺ��̽��� ���̵� ����ϰ� ī��Ʈ���� �ʱ�ȭ��Ų��.
		adminJDBC.MergeCount(id);
		
		// Ŭ���̾�Ʈ���� �Ϸ�Ǿ��ٰ� ������
		resp.getWriter().print(Constants.SUCCESS);
		
	}
}
