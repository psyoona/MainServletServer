package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeFour extends ImageMerge {

	public ImageMergeFour(String leftOrRight, String fileOne, String fileTwo, String fileThree, String fileFour, String emotion,
			String loginID) {
		this.leftOrRight = leftOrRight;
		this.fileName = new String[4];
		this.fileName[0] = fileOne;
		this.fileName[1] = fileTwo;
		this.fileName[2] = fileThree;
		this.fileName[3] = fileFour;
		this.emotion = emotion;
		this.loginID = loginID;
	}

	public BufferedImage merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[0])));
			BufferedImage image2 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[1])));
			BufferedImage image3 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[2])));
			BufferedImage image4 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[3])));
			selectBackground(emotion);
			
			Graphics2D graphics = (Graphics2D) background.getGraphics();
			graphics.drawImage(image1, 100, Constants.MERGE_Y4, null);
			graphics.drawImage(image2, 610, Constants.MERGE_Y4, null);
			graphics.drawImage(image3, 100, Constants.MERGE_Y4+750, null);
			graphics.drawImage(image4, 610, Constants.MERGE_Y4+750, null);

			// 구분되는 파일명을 만들어주기 위함
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS");

			// 파일명 생성
			String fileName = "four" + sdf.format(currentTime).toString();

			// 파일 경로 생성
			String path = makeMergePath(this.loginID, fileName);

			ImageIO.write(background, "jpg", new File(path));
			result = ImageIO.read(new File(path));			

			System.out.println(Constants.COMPLETE);

			return result;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;

	}
}
