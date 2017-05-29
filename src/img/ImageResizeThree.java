package img;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeThree extends ImageResize{	

	public BufferedImage resize(String fileOne, String fileTwo, String fileThree, String emotion, String loginID) {
		try {
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" +fileOne));
			image2 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileTwo));
			image3 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileThree));
			System.out.println(emotion);
			happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/"+emotion + ".jpg"));
			
			resizeImage1 = image1.getScaledInstance(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() / 2 - happy.getHeight() / 20, Image.SCALE_SMOOTH);
			resizeImage2 = image2.getScaledInstance(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() / 2 - happy.getHeight() / 20, Image.SCALE_SMOOTH);
			resizeImage3 = image3.getScaledInstance(happy.getWidth() - happy.getWidth() / 21 + 3,
					happy.getHeight() / 2 - happy.getHeight() / 20, Image.SCALE_SMOOTH);

			newImage1 = new BufferedImage(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() / 2 - happy.getHeight() / 20, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) newImage1.getGraphics();
			g.drawImage(resizeImage1, 0, 0, null);
			newImage2 = new BufferedImage(happy.getWidth() / 2 - happy.getWidth() / 42,
					happy.getHeight() / 2 - happy.getHeight() / 20, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) newImage2.getGraphics();
			g2.drawImage(resizeImage2, 0, 0, null);
			newImage3 = new BufferedImage(happy.getWidth() - happy.getWidth() / 21 + 3,
					happy.getHeight() / 2 - happy.getHeight() / 20, BufferedImage.TYPE_INT_RGB);
			Graphics2D g3 = (Graphics2D) newImage3.getGraphics();
			g3.drawImage(resizeImage3, 0, 0, null);

			g.dispose();
			g2.dispose();
			g3.dispose();
			ImageIO.write(newImage1, "jpg", new File(Constants.IMG_PATH + loginID +"/album/" + fileOne));
			ImageIO.write(newImage2, "jpg", new File(Constants.IMG_PATH + loginID + "/album/" + fileTwo));
			ImageIO.write(newImage3, "jpg", new File(Constants.IMG_PATH + loginID + "/album/" + fileThree));
			ImageMergeThree imgmer = new ImageMergeThree(fileOne, fileTwo, fileThree, emotion, loginID);
			result = imgmer.merge();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}
}
