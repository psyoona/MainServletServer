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
			
			Resacle.scale(image1, Constants.IMG_PATH + loginID +"/" + "album/" + fileOne,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT3);
			Resacle.scale(image2, Constants.IMG_PATH + loginID +"/" + "album/" + fileTwo,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT3);
			Resacle.scale(image3, Constants.IMG_PATH + loginID +"/" + "album/" + fileThree,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT3);
			
			ImageMergeThree imgmer = new ImageMergeThree(fileOne, fileTwo, fileThree, emotion, loginID);
			result = imgmer.merge();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}
}
