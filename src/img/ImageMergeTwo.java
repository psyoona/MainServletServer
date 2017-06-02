package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeTwo extends ImageMerge{	
	
	public ImageMergeTwo(String fileOne, String fileTwo, String emotion, String loginID) {
		this.fileName = new String[2];
		
		this.fileName[0] = fileOne;
		this.fileName[1] = fileTwo;
		this.emotion = emotion;
		this.loginID = loginID;
	}

	public BufferedImage merge() {
		System.out.println("merge()함수 시도 중");
		try {
			BufferedImage image1 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[0])));
			BufferedImage image2 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[1])));
			selectBackground(emotion);
			
			Graphics2D graphics = (Graphics2D) background.getGraphics();
			graphics.drawImage(image1, Constants.MERGEWIDTH2, Constants.MERGEHEIGHT2, null);
			graphics.drawImage(image2, Constants.MERGEWIDTH2+305, Constants.MERGEHEIGHT2, null);
			
			// 구분되는 파일명을 만들어주기 위함
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_ms");
			
			// 파일명 생성
			String fileName = "two"+sdf.format(currentTime).toString();
			
			// 파일 경로 생성
			String path = makeMergePath(this.loginID, fileName);
			
			ImageIO.write(background, "jpg", new File(path));			
			result = ImageIO.read(new File(path));
			
			// 이미지 합성이 완료된 경우 imgMerge 테이블에 저장한다.
//			adminJDBC = new JDBC();
//			System.out.println("Save Merge Start");
//			adminJDBC.saveMerge(this.loginID, fileName, emotion);
			
			
			// 그 이후 mergeCount 값을 1증가시킨다.
//			System.out.println("addCount Start");
//			adminJDBC.addCount(this.loginID);
			
			// 이미지 Merge가 완료되었음을 의미
			System.out.println(Constants.COMPLETE);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return result;
	}
}