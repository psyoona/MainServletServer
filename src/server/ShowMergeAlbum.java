package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;

public class ShowMergeAlbum {
	String loginID;
	JDBCDriver adminJDBC;
	String[] album;
	
	public void showMergeAlbum(String[] array, HttpServletResponse resp) throws IOException{
		// 들어온 JSON 데이터에서 id 값을 찾아낸다.
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}
		}
		
		// 데이터베이스에서 앨범목록을 찾아서 리턴한다.
		adminJDBC = new JDBCDriver();
		album = adminJDBC.showMergeAlbum(loginID);
		
		StringBuilder sender = new StringBuilder();
		for(int i=0; i<album.length; i++){
			sender.append(album[i]);
			sender.append(",");
		}
		System.out.println(sender.toString());
		//System.out.println(album);
		
		// 모든 작업이 끝난 후 파일명 스트링을 전달한다.
		resp.getWriter().print(sender);
	}
}
