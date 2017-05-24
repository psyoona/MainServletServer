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
 * 사진에 문자열을 입력하는 클래스
 * 
 */

public class InsertText {
	public static void insertText(String text) {
		long startTime = System.currentTimeMillis();
		String subject = "사진의 제목";
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
			System.out.println("이미지 불러오다가 에러 발생");
			e.printStackTrace();
		}

		int imgWidth = bi.getWidth();
		int imgHeight = bi.getHeight();
		print("loadImage\nwidth : " + imgWidth + ", height : " + imgHeight);
		Graphics2D g2 = null;
		g2 = bi.createGraphics();
		// text에 적용할 폰트 생성, 아래 폰트는 시스템에 설치 되어 있어야 사용할 수 있음
		Font font = new Font("Gungsuh", Font.BOLD, 26);
		// 가운데 정렬하기 위해, text의 width구하기
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2 = font.getStringBounds(subject, frc);
		int textWidth = (int) r2.getWidth();
		float paddingleft = 0;

		// 입력하는 문자의 가용 넓이
		int textWide = 439;
		paddingleft = ((textWide - textWidth) / 2) + 20;
		print("textWidth : " + textWidth);
		print("paddingleft : " + paddingleft);
		
		// 폰트 색상 설정
		g2.setColor(Color.black);
		
		// 폰트 종류 설정
		g2.setFont(font);

		// 이미지에 텍스트 사입. (text,x축,y축)
		/* g2.drawString(subject, paddingleft, (float) (imgHeight * 0.8247)); */
		g2.drawString(year, 336, 773);
		g2.drawString(subject, 430, 503);
		g2.dispose();

		try {
			ImageIO.write(bi, "jpg", makeImage);
		} catch (IOException e) {
			System.out.print("새로운 이미지 저장하다가 에러 나쓔..ㅜㅜ");
			e.printStackTrace();
		}
		print("text length : " + subject.length());
		print("end make image");

		long endTime = System.currentTimeMillis();
		print("currentTimeMillis()형태\n시작시간 : " + startTime + ", 종료시간 : " + endTime);
		print("이미지 생성하는데 걸린 시간 [" + ((endTime - startTime) / 1000.0) + "]");
	}

	public static void print(String str) {
		System.out.print("\n" + str + "\n");
	}

}