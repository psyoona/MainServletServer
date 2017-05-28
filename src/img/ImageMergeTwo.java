package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.Constants;
import server.JDBC;

public class ImageMergeTwo extends ImageMerge{	
	
	public ImageMergeTwo(String fileOne, String fileTwo, String emotion, String loginID) {
		this.filename = new String[2];
		
		this.filename[0] = fileOne;
		this.filename[1] = fileTwo;
		this.emotion = emotion;
		this.loginID = "/"+loginID;
	}

	public BufferedImage merge() {
		System.out.println("merge()함수 시도 중");
		try {
			BufferedImage image1 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[0]));
			BufferedImage image2 = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/album/" +filename[1]));
			BufferedImage happy = null;
			if(emotion.equals("happiness")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));
			}else if(emotion.equals("neutral")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/neutral.jpg"));
			}else if(emotion.equals("sadness")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/sadness.jpg"));
			}else if(emotion.equals("surprise")){
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/surprise.jpg"));
			}else{
				happy = ImageIO.read(new File(Constants.IMG_PATH + "emotion/happy.jpg"));
			}
			
			Graphics2D graphics = (Graphics2D) happy.getGraphics();
			graphics.drawImage(image1, 15, happy.getHeight() / 20, null);
			graphics.drawImage(image2, happy.getWidth() / 2, happy.getHeight() / 20, null);
			
			ImageIO.write(happy, "jpg", new File(Constants.IMG_PATH + this.loginID + "/merge/" + "frame2.jpg"));
			
			result = ImageIO.read(new File(Constants.IMG_PATH + this.loginID + "/merge/" + "frame2.jpg"));
			
			// 이미지 합성이 완료된 경우 imgMerge 테이블에 저장한다.
			adminJDBC = new JDBC();
			System.out.println("Save Merge Start");
			adminJDBC.saveMerge(this.loginID, Constants.IMG_PATH + this.loginID + "/merge/" + "frame2.jpg", emotion);
			// 그 이후 mergeCount 값을 1증가시킨다.
			System.out.println("addCount Start");
			adminJDBC.addCount(this.loginID);
			
			// 이미지 Merge가 완료되었음을 의미
			System.out.println(Constants.COMPLETE);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return result;
	}
}