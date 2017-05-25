package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ShowAlbum {
	String loginID;
	JDBC adminJDBC;
	String[] album;
	
	public void showAlbum(String[] array, HttpServletResponse resp) throws IOException{
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}
		}
		
		adminJDBC = new JDBC();
		album = adminJDBC.showAlbum(loginID);
		
		if(album != null){
			// ������ ����� ������ �ִ� ���
			// �켱 ������ ������ ���� �����Ѵ�.
			resp.getWriter().print(album.length);
			for(int i = 0; i<album.length; i++){
				resp.getWriter().print(album[i]);	
			}			
		}else{
			// ������ ����� ������ ���� ���
			resp.getWriter().print("notHave");
		}
	}
}