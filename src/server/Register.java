package server;

public class Register {
	String id=null, pw=null, nickname=null;	
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void register(String[] array){		
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
		// UserList 데이터베이스에 아이디, 비밀번호, 닉네임을 등록한다.
		adminJDBC.registerDB(id, pw, nickname);
		// MergeCount 데이터베이스에 아이디를 등록하고 카운트값을 초기화시킨다.
		adminJDBC.MergeCount(id);
		
	}
}
