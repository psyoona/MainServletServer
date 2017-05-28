package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;
import server.JDBC;

public class ImageMergeFour extends ImageMerge {

	public ImageMergeFour(String[] filename, String emotion, String loginID) {
		this.filename = new String[4];

		this.filename[0] = filename[0];
		this.filename[1] = filename[1];
		this.filename[2] = filename[2];
		this.filename[3] = filename[3];

		this.emotion = emotion;
		this.loginID = "/" + loginID;
	}

	public ImageMergeFour(String fileOne, String fileTwo, String fileThree, String fileFour, String emotion,
			String loginID) {
		this.filename[0] = fileOne;
		this.filename[1] = fileTwo;
		this.filename[2] = fileThree;
		this.filename[3] = fileFour;
	}

	public BufferedImage merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(Constants.IMG_PATH + filename[0]));
			BufferedImage image2 = ImageIO.read(new File(Constants.IMG_PATH + filename[1]));
			BufferedImage image3 = ImageIO.read(new File(Constants.IMG_PATH + filename[2]));
			BufferedImage image4 = ImageIO.read(new File(Constants.IMG_PATH + filename[3]));

			BufferedImage happy = ImageIO.read(new File(Constants.IMG_PATH));

			// BufferedImage mergedImage = new
			// BufferedImage(happy.getWidth(),happy.getHeight(),
			// BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) happy.getGraphics();
			graphics.drawImage(image1, 15, happy.getHeight() / 20, null);
			graphics.drawImage(image2, happy.getWidth() / 2, happy.getHeight() / 20, null);
			graphics.drawImage(image3, 15, happy.getHeight() / 2 + 5, null);
			graphics.drawImage(image4, happy.getWidth() / 2, happy.getHeight() / 2 + 5, null);

			ImageIO.write(happy, "jpg", new File(Constants.IMG_PATH + this.loginID + "/merge/" + "frame4.jpg"));
			result = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/merge/" + "frame4.jpg"));
			adminJDBC = new JDBC();
			adminJDBC.saveMerge(this.loginID, Constants.IMG_PATH + this.loginID + "/merge/" + "frame4.jpg", emotion);
			// 그 이후 mergeCount 값을 1증가시킨다.
			adminJDBC.addCount(this.loginID);
			System.out.println(Constants.COMPLETE);
			
			return result;
		} catch (IOException ioe) {
			ioe.printStackTrace();

			// TODO: handle exception
		}
		return result;

	}
}
