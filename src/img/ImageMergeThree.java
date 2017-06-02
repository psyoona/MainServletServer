package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeThree extends ImageMerge {

	public ImageMergeThree(String fileOne, String fileTwo, String fileThree, String emotion, String loginID) {
		this.fileName = new String[3];
		this.fileName[0] = fileOne;
		this.fileName[1] = fileTwo;
		this.fileName[2] = fileThree;
		this.emotion = emotion;
		this.loginID = loginID;
	}

	public BufferedImage merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[0])));
			BufferedImage image2 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[1])));
			BufferedImage image3 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[2])));
			selectBackground(emotion);
			
			Graphics2D graphics = (Graphics2D) background.getGraphics();
			graphics.drawImage(image1, 15, (background.getHeight() / 20)+50, null);
			graphics.drawImage(image2, background.getWidth() / 2, (background.getHeight() / 20)+50, null);
			graphics.drawImage(image3, 15, (background.getHeight() / 2 + 5) +50, null);

			// 구분되는 파일명을 만들어주기 위함
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_ms");

			// 파일명 생성
			String fileName = "three" + sdf.format(currentTime).toString();

			// 파일 경로 생성
			String path = makeMergePath(this.loginID, fileName);

			ImageIO.write(background, "jpg", new File(path));
			result = ImageIO.read(new File(path));

			// 이미지 합성이 완료된 경우 imgMerge 테이블에 저장한다.
//			adminJDBC = new JDBC();
//			adminJDBC.saveMerge(this.loginID, fileName, emotion);
			
//			// 그 이후 mergeCount 값을 1증가시킨다.
//			adminJDBC.addCount(this.loginID);
			System.out.println(Constants.COMPLETE);
			return result;

		} catch (IOException ioe) {
			ioe.printStackTrace();

			// TODO: handle exception
		}
		return result;
	}
}
