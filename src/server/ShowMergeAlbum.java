package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;

public class ShowMergeAlbum {
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
		album = adminJDBC.showMergeAlbum(loginID);
		
		if(album != null){
			
		}else{
			// ������ ����� ������ ���� ���
			resp.getWriter().print("notHave");
		}
	}
}
