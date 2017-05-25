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
		adminJDBC.registerDB(id, pw, nickname);
		
	}
}
