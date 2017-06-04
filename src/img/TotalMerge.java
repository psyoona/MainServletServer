package img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import JDBC.JDBC;

public class TotalMerge extends ImageMerge{
	BufferedImage background;

	public TotalMerge(BufferedImage image1, BufferedImage image2, String loginID) {
		try {
			this.loginID = loginID;
			background = ImageIO.read(new File("D://img/emotion/panel.jpg"));
			Graphics2D graphics = (Graphics2D) background.getGraphics();
			graphics.drawImage(image1, 0, 0, null);
			graphics.drawImage(image2, background.getWidth()/2, 0, null);

			// ���еǴ� ���ϸ��� ������ֱ� ����
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS");

			// ���ϸ� ����
			String fileName = "final" + sdf.format(currentTime).toString();

			// ���� ��� ����
			String path = makeFinalPath(loginID, fileName);

			ImageIO.write(background, "jpg", new File(path));
			
			adminJDBC = new JDBC();
			System.out.println("Save Merge Start");
			adminJDBC.saveMerge(this.loginID, fileName, "happy");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
