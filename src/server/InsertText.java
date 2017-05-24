package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/*
 * ������ ���ڿ��� �Է��ϴ� Ŭ����
 * 
 */

public class InsertText {
	public static void insertText(String text) {
		long startTime = System.currentTimeMillis();
		String subject = "������ ����";
		String year = "2017";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		File makeImage = new File("D:/Server_project/MainServer/img/fixed" + sdf.format(date) + ".jpg");
		print("saved New image name : " + makeImage.toString());
		File loadImage = new File("D:/Server_project/MainServer/img");
		BufferedImage bi = null;

		try {
			bi = ImageIO.read(loadImage);
		} catch (IOException e) {
			System.out.println("�̹��� �ҷ����ٰ� ���� �߻�");
			e.printStackTrace();
		}

		int imgWidth = bi.getWidth();
		int imgHeight = bi.getHeight();
		print("loadImage\nwidth : " + imgWidth + ", height : " + imgHeight);
		Graphics2D g2 = null;
		g2 = bi.createGraphics();
		// text�� ������ ��Ʈ ����, �Ʒ� ��Ʈ�� �ý��ۿ� ��ġ �Ǿ� �־�� ����� �� ����
		Font font = new Font("Gungsuh", Font.BOLD, 26);
		// ��� �����ϱ� ����, text�� width���ϱ�
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2 = font.getStringBounds(subject, frc);
		int textWidth = (int) r2.getWidth();
		float paddingleft = 0;

		// �Է��ϴ� ������ ���� ����
		int textWide = 439;
		paddingleft = ((textWide - textWidth) / 2) + 20;
		print("textWidth : " + textWidth);
		print("paddingleft : " + paddingleft);
		
		// ��Ʈ ���� ����
		g2.setColor(Color.black);
		
		// ��Ʈ ���� ����
		g2.setFont(font);

		// �̹����� �ؽ�Ʈ ����. (text,x��,y��)
		/* g2.drawString(subject, paddingleft, (float) (imgHeight * 0.8247)); */
		g2.drawString(year, 336, 773);
		g2.drawString(subject, 430, 503);
		g2.dispose();

		try {
			ImageIO.write(bi, "jpg", makeImage);
		} catch (IOException e) {
			System.out.print("���ο� �̹��� �����ϴٰ� ���� ���o..�̤�");
			e.printStackTrace();
		}
		print("text length : " + subject.length());
		print("end make image");

		long endTime = System.currentTimeMillis();
		print("currentTimeMillis()����\n���۽ð� : " + startTime + ", ����ð� : " + endTime);
		print("�̹��� �����ϴµ� �ɸ� �ð� [" + ((endTime - startTime) / 1000.0) + "]");
	}

	public static void print(String str) {
		System.out.print("\n" + str + "\n");
	}

}