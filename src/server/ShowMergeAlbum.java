package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;

public class ShowMergeAlbum {
	String loginID;
	JDBC adminJDBC;
	String[] album;
	
	public void showMergeAlbum(String[] array, HttpServletResponse resp) throws IOException{
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}
		}
		
		adminJDBC = new JDBC();
		album = adminJDBC.showMergeAlbum(loginID);
		//System.out.println(album);
		// 모든 작업이 끝난 후 리턴
		resp.getWriter().print(Constants.SUCCESS);
	}
}
