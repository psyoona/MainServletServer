package img;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageResize {
	protected BufferedImage image1;
	protected BufferedImage image2;
	protected BufferedImage image3;
	protected BufferedImage image4;

	protected BufferedImage result;
	
	protected Image resizeImage1;
	protected Image resizeImage2;
	protected Image resizeImage3;
	protected Image resizeImage4;
	protected BufferedImage newImage1;
	protected BufferedImage newImage2;
	protected BufferedImage newImage3;
	protected BufferedImage newImage4;
	
	protected BufferedImage background;
	
	public ImageResize(){
		// Constructor
	}
	
	public void selectBackground(String emotion) throws IOException{
		if(emotion.equals(Constants.HAPPINESS)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happiness.jpg"));
		}else if(emotion.equals(Constants.NEUTRAL)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/neutral.jpg"));
		}else if(emotion.equals(Constants.SADNESS)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/sadness.jpg"));
		}else if(emotion.equals(Constants.SURPRISE)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/surprise.jpg"));
		}else if(emotion.equals(Constants.ANGER)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/anger.jpg"));
		}else if(emotion.equals(Constants.CONTEMPT)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/contempt.jpg"));
		}else if(emotion.equals(Constants.CONTEMPT)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/disgust.jpg"));
		}else if(emotion.equals(Constants.FEAR)){
			background = ImageIO.read(new File(Constants.IMG_PATH + "emotion/fear.jpg"));
		}
	}
	
	public BufferedImage resize(String fileOne, String fileTwo, String emotion, String loginID){
		return null;}
	public BufferedImage resize(String fileOne, String fileTwo, String fileThree, String emotion, String loginID){
		return null;}
	public BufferedImage resize(String fileOne, String fileTwo, String fileThree, String fileFour, String emotion, String loginID){
		return null;}
}
