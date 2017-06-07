package server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;

public class ShowMergeAlbum {
	String loginID;
	JDBCDriver adminJDBC;
	String[] album;
	
	public void showMergeAlbum(String[] array, HttpServletResponse resp) throws IOException{
		// ���� JSON �����Ϳ��� id ���� ã�Ƴ���.
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}
		}
		
		// �����ͺ��̽����� �ٹ������ ã�Ƽ� �����Ѵ�.
		adminJDBC = new JDBCDriver();
		album = adminJDBC.showMergeAlbum(loginID);
		
		StringBuilder sender = new StringBuilder();
		for(int i=0; i<album.length; i++){
			sender.append(album[i]);
			sender.append(",");
		}
		System.out.println(sender.toString());
		//System.out.println(album);
		
		// ��� �۾��� ���� �� ���ϸ� ��Ʈ���� �����Ѵ�.
		resp.getWriter().print(sender);
	}
}
