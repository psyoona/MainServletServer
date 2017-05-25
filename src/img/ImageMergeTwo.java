package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeTwo extends ImageMerge{	
	
	public ImageMergeTwo(String[] filename, String emotion, String loginID) {
		this.filename = new String[2];
		
		this.filename[0] = filename[0];
		this.filename[1] = filename[1];
		this.emotion = emotion;
		this.loginID = "/"+loginID;
	}

	public void merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[0]));
			BufferedImage image2 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[1]));
			BufferedImage happy = null;
			if(emotion.equals("happiness")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));
			}else if(emotion.equals("neutral")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/neutral.jpg"));
			}else if(emotion.equals("sadness")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/sadness.jpg"));
			}else if(emotion.equals("surprise")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/surprise.jpg"));
			}else{
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));
			}
			
			Graphics2D graphics = (Graphics2D) happy.getGraphics();
			graphics.drawImage(image1, 15, happy.getHeight() / 20, null);
			graphics.drawImage(image2, happy.getWidth() / 2, happy.getHeight() / 20, null);
			ImageIO.write(happy, "jpg", new File(Constants.IMG_PATH + this.loginID + "/album/" + "frame.jpg"));		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println(Constants.COMPLETE);
	}
}