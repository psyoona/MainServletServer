package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeTwo extends ImageResize{	

	public BufferedImage resize(String leftOrRight, String fileOne, String fileTwo, String emotion, String loginID) {
		try {			
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileOne));
			image2 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileTwo));
			
			Resacle.scale(image1, Constants.IMG_PATH + loginID +"/" + "album/" + fileOne,"jpg", Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2);
			Resacle.scale(image2, Constants.IMG_PATH + loginID +"/" + "album/" + fileTwo,"jpg", Constants.RESIZEWIDTH2, Constants.RESIZEHEIGHT2);
			
			ImageMergeTwo imgmer = new ImageMergeTwo(leftOrRight, fileOne, fileTwo, emotion, loginID);
			result = imgmer.merge();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}
