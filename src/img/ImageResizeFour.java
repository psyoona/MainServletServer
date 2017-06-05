package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeFour extends ImageResize{
	

	public BufferedImage resize(String fileOne, String fileTwo, String fileThree, String fileFour, String emotion, String loginID) {
		try {
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileOne));
			image2 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileTwo));
			image3 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileThree));
			image4 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileFour));

			Resacle.scale(image1, Constants.IMG_PATH + loginID +"/" + "album/" + fileOne,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT4);
			Resacle.scale(image2, Constants.IMG_PATH + loginID +"/" + "album/" + fileTwo,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT4);
			Resacle.scale(image3, Constants.IMG_PATH + loginID +"/" + "album/" + fileThree,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT4);
			Resacle.scale(image4, Constants.IMG_PATH + loginID +"/" + "album/" + fileFour,"jpg", Constants.RESIZEWIDTH3, Constants.RESIZEHEIGHT4);

			ImageMergeFour imgmer = new ImageMergeFour(fileOne, fileTwo, fileThree, fileFour, emotion, loginID);
			result = imgmer.merge();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			// TODO: handle exception
		}
		return result;
	}
}
