package img;

import java.awt.image.BufferedImage;

import server.JDBC;

public class ImageMerge {
	protected String loginID;
	protected String[] filename;
	protected String emotion;
	protected JDBC adminJDBC;
	
	BufferedImage result;
	
	public ImageMerge(){
		// Constructor
	}
	
	public ImageMerge(String[] filename,String emotion){
		// Constructor
	}
}
