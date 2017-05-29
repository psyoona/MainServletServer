package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import JDBC.JDBC;
import server.Constants;

public class ImageMergeFour extends ImageMerge {

	public ImageMergeFour(String fileOne, String fileTwo, String fileThree, String fileFour, String emotion,
			String loginID) {
		this.fileName = new String[4];
		this.fileName[0] = fileOne;
		this.fileName[1] = fileTwo;
		this.fileName[2] = fileThree;
		this.fileName[3] = fileFour;
	}

	public BufferedImage merge() {
		try {
			BufferedImage image1 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[0])));
			BufferedImage image2 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[1])));
			BufferedImage image3 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[2])));
			BufferedImage image4 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[3])));

			BufferedImage happy = ImageIO.read(new File(Constants.IMG_PATH));

			// BufferedImage mergedImage = new
			// BufferedImage(happy.getWidth(),happy.getHeight(),
			// BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) happy.getGraphics();
			graphics.drawImage(image1, 15, happy.getHeight() / 20, null);
			graphics.drawImage(image2, happy.getWidth() / 2, happy.getHeight() / 20, null);
			graphics.drawImage(image3, 15, happy.getHeight() / 2 + 5, null);
			graphics.drawImage(image4, happy.getWidth() / 2, happy.getHeight() / 2 + 5, null);

			// 구분되는 파일명을 만들어주기 위함
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_ms");

			// 파일명 생성
			String fileName = "four" + sdf.format(currentTime).toString();

			// 파일 경로 생성
			String path = makeMergePath(this.loginID, fileName);

			ImageIO.write(happy, "jpg", new File(path));
			result = ImageIO.read(new File(path));
			
			adminJDBC = new JDBC();
			adminJDBC.saveMerge(this.loginID, fileName, emotion);
			// 그 이후 mergeCount 값을 1증가시킨다.
			adminJDBC.addCount(this.loginID);
			System.out.println(Constants.COMPLETE);

			return result;
		} catch (IOException ioe) {
			ioe.printStackTrace();

			// TODO: handle exception
		}
		return result;

	}
}
