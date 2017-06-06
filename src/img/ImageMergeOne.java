package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import server.Constants;

public class ImageMergeOne extends ImageMerge {
	public ImageMergeOne(String leftOrRight, String fileOne, String emotion, String loginID) {
		this.leftOrRight = leftOrRight;
		this.fileName = new String[1];
		
		this.fileName[0] = fileOne;
		this.emotion = emotion;
		this.loginID = loginID;
	}
	
	public BufferedImage merge() {
		System.out.println("merge()�Լ� �õ� ��");
		try {
			BufferedImage image1 = ImageIO.read(new File(makeAlbumPath(this.loginID, fileName[0])));
			selectBackground(emotion);

			Graphics2D graphics = (Graphics2D) background.getGraphics();
			graphics.drawImage(image1, Constants.MERGE_X1, Constants.MERGE_Y1, null);

			// ���еǴ� ���ϸ��� ������ֱ� ����
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS");

			// ���ϸ� ����
			String fileName = "two" + sdf.format(currentTime).toString();

			// ���� ��� ����
			String path = makeMergePath(this.loginID, fileName);

			ImageIO.write(background, "jpg", new File(path));
			result = ImageIO.read(new File(path));

			// �̹��� Merge�� �Ϸ�Ǿ����� �ǹ�
			System.out.println(Constants.COMPLETE);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result;
	}
}
