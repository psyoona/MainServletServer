package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeTwo extends ImageMerge{
	String[] filename = new String[2];
	String emotion;
	String loginID;
	
	public ImageMergeTwo(String[] filename, String emotion, String loginID) {
		this.filename[0] = filename[0];
		this.filename[1] = filename[1];
		this.emotion = emotion;
		this.loginID = "/"+loginID;
	}

	public void merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[0]));
			BufferedImage image2 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[1]));
			BufferedImage happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));

			// BufferedImage mergedImage = new
			// BufferedImage(happy.getWidth(),happy.getHeight(),
			// BufferedImage.TYPE_INT_RGB);
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