package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeThree extends ImageMerge{	

	public ImageMergeThree(String[] filename, String emotion, String loginID) {
		this.filename = new String[3];
		
		this.filename[0] = filename[0];
		this.filename[1] = filename[1];
		this.filename[2] = filename[2];
		this.emotion = emotion;
		this.loginID = "/"+loginID;
	}

	public void merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[0]));
			BufferedImage image2 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[1]));
			BufferedImage image3 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[1]));
			BufferedImage happy = ImageIO.read(new File(Constants.IMG_PATH + emotion + ".jpg"));

			// BufferedImage mergedImage = new
			// BufferedImage(happy.getWidth(),happy.getHeight(),
			// BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) happy.getGraphics();
			graphics.drawImage(image1, 15, happy.getHeight() / 20, null);
			graphics.drawImage(image2, happy.getWidth() / 2, happy.getHeight() / 20, null);
			graphics.drawImage(image3, 15, happy.getHeight() / 2 + 5, null);
			ImageIO.write(happy, "jpg", new File(Constants.IMG_PATH));

		} catch (IOException ioe) {
			ioe.printStackTrace();

			// TODO: handle exception
		}
		System.out.println(Constants.COMPLETE);

	}
}
