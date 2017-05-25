package img;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeTwo extends ImageResize{	

	public void resize(String filename[], String emotion, String loginID) {
		try {			
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + filename[0]));
			image2 = ImageIO.read(new File(Constants.IMG_PATH + loginID + filename[1]));
			happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));
			resizeImage1 = image1.getScaledInstance(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() - happy.getHeight() / 6, Image.SCALE_SMOOTH);
			resizeImage2 = image2.getScaledInstance(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() - happy.getHeight() / 6, Image.SCALE_SMOOTH);
			newImage1 = new BufferedImage(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() - happy.getHeight() / 6, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) newImage1.getGraphics();
			g.drawImage(resizeImage1, 0, 0, null);
			newImage2 = new BufferedImage(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() - happy.getHeight() / 6, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) newImage2.getGraphics();
			g2.drawImage(resizeImage2, 0, 0, null);
			g.dispose();
			g2.dispose();
			ImageIO.write(newImage1, "jpg", new File(Constants.IMG_PATH + loginID + "album/" + filename[0]));
			ImageIO.write(newImage2, "jpg", new File(Constants.IMG_PATH + loginID + "album/" + filename[1]));
			ImageMergeTwo imgmer = new ImageMergeTwo(filename, emotion, loginID);
			imgmer.merge();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
