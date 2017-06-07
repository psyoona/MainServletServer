package img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResizeOne extends ImageResize {
	public BufferedImage resize(String leftOrRight, String fileOne, String emotion, String loginID) {
		try {			
			image1 = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/" + fileOne));
			
			Resacle.scale(image1, Constants.IMG_PATH + loginID +"/" + "album/" + fileOne,"jpg", Constants.RESIZEWIDTH1, Constants.RESIZEHEIGHT1);
			
			ImageMergeOne imgmer = new ImageMergeOne(leftOrRight, fileOne, emotion, loginID);
			result = imgmer.merge();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}
}
