package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeTwo extends ImageResize{	

	public BufferedImage resize(String fileOne, String fileTwo, String emotion, String loginID) {
		try {			
			System.out.println("°æ·Î: "+Constants.IMG_PATH + loginID + "/" + fileOne);
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileOne));
			image2 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileTwo));
			selectBackground(emotion);
			
//			resizeImage1 = image1.getScaledInstance(Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2, Image.SCALE_SMOOTH);
//			resizeImage2 = image2.getScaledInstance(Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2, Image.SCALE_SMOOTH);
//			newImage1 = new BufferedImage(Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2, BufferedImage.TYPE_INT_RGB);
//			Graphics2D g = (Graphics2D) newImage1.getGraphics();
//			g.drawImage(resizeImage1, 0, 0, null);
//			
//			newImage2 = new BufferedImage(Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2, BufferedImage.TYPE_INT_RGB);
//			Graphics2D g2 = (Graphics2D) newImage2.getGraphics();
//			g2.drawImage(resizeImage2, 0, 0, null);
//			g.dispose();
//			g2.dispose();
//			ImageIO.write(newImage1, "jpg", new File(Constants.IMG_PATH + loginID +"/" + "album/" + fileOne));
//			ImageIO.write(newImage2, "jpg", new File(Constants.IMG_PATH + loginID +"/" + "album/" + fileTwo));
//			System.out.println(fileOne+"  sklslslslslslslslsls");
//			System.out.println(fileTwo+"  sklslslslslslslslsls");
			
			Test.scale(image1, Constants.IMG_PATH + loginID +"/" + "album/" + fileOne,"jpg", Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2);
			Test.scale(image2, Constants.IMG_PATH + loginID +"/" + "album/" + fileTwo,"jpg", Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2);
			ImageMergeTwo imgmer = new ImageMergeTwo(fileOne, fileTwo, emotion, loginID);
			result = imgmer.merge();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}
