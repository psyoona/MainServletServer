package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import JDBC.JDBC;
import server.Constants;

public class ImageMerge {
	protected String loginID;
	protected String[] fileName;
	protected String emotion;
	protected JDBC adminJDBC;
	protected BufferedImage background = null;
	
	BufferedImage result;
	
	public ImageMerge(){
		// Constructor
	}
	
	public ImageMerge(String[] filename,String emotion){
		// Constructor
	}
	
	public void selectBackground(String emotion) throws IOException{
		if(emotion.equals(Constants.HAPPINESS)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happiness.jpg"));
		}else if(emotion.equals(Constants.NEUTRAL)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/neutral.jpg"));
		}else if(emotion.equals(Constants.SADNESS)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/sadness.jpg"));
		}else if(emotion.equals(Constants.SURPRISE)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/surprise.jpg"));
		}else if(emotion.equals(Constants.ANGER)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/anger.jpg"));
		}else if(emotion.equals(Constants.CONTEMPT)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/contempt.jpg"));
		}else if(emotion.equals(Constants.CONTEMPT)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/disgust.jpg"));
		}else if(emotion.equals(Constants.FEAR)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/fear.jpg"));
		}
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
	
	public static String makeFilterPath(String loginID, String fileName){
		StringBuilder build = new StringBuilder();
		build.append(Constants.IMG_PATH);
		build.append(loginID);
		build.append("/filter/");
		build.append(fileName);		
		 
		return build.toString();
	}
	
	public static String makeFinalPath(String loginID, String fileName){
		StringBuilder build = new StringBuilder();
		build.append(Constants.IMG_PATH);
		build.append(loginID);
		build.append("/final/");
		build.append(fileName);		
		build.append(".jpg");		
		
		return build.toString();		
	}
	
	public void makeEmotionPath(){
		
	}
}
