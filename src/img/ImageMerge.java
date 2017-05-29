package img;

import java.awt.image.BufferedImage;

import server.Constants;
import server.JDBC;

public class ImageMerge {
	protected String loginID;
	protected String[] fileName;
	protected String emotion;
	protected JDBC adminJDBC;
	
	BufferedImage result;
	
	public ImageMerge(){
		// Constructor
	}
	
	public ImageMerge(String[] filename,String emotion){
		// Constructor
	}
	
	public String makeAlbumPath(String loginID, String fileName){
		StringBuilder build = new StringBuilder();
		build.append(Constants.IMG_PATH);
		build.append(loginID);
		build.append("/album/");
		build.append(fileName);
		
		return build.toString();
	}
	
	public String makeMergePath(String loginID, String fileName){
		StringBuilder build = new StringBuilder();
		build.append(Constants.IMG_PATH);
		build.append(loginID);
		build.append("/merge/");
		build.append(fileName);		
		build.append(".jpg");		
		 
		return build.toString();
	}
	
	public void makeEmotionPath(){
		
	}
}
