package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;

public class ShowAlbum {
	String loginID;
	JDBCDriver adminJDBC;
	String[] album;
	
	public void showMergeAlbum(String[] array, HttpServletResponse resp) throws IOException{
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}
		}
		
		adminJDBC = new JDBCDriver();
		album = adminJDBC.showAlbum(loginID);
		
		if(album != null){
			// 서버에 저장된 사진이 있는 경우
			// 우선 사진의 갯수를 먼저 전송한다.
			resp.getWriter().print(album.length);
			for(int i = 0; i<album.length; i++){
				resp.getWriter().print(album[i]);	
			}			
		}else{
			// 서버에 저장된 사진이 없는 경우
			resp.getWriter().print("notHave");
		}
	}
}
