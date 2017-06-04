package server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.jhlabs.image.ChromeFilter;
import com.jhlabs.image.DitherFilter;
import com.jhlabs.image.ExposureFilter;
import com.jhlabs.image.GrayFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.LensBlurFilter;
import com.jhlabs.image.LookupFilter;
import com.jhlabs.image.SolarizeFilter;
import com.jhlabs.image.ThresholdFilter;

import JDBC.JDBC;

public class FilterImg {
	protected static BufferedImage orgImage;
	protected static BufferedImage fileterImage;
	String loginID;
	String fileName;
	JDBC adminJDBC;

	public FilterImg() {

	}

	public void saveImage(String[] array, HttpServletResponse resp) throws IOException {
		StringBuilder result = new StringBuilder();
		
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("id")) {
				loginID = array[i + 2];
			} else if (array[i].equals("filename")) {
				fileName = array[i + 2];
			}
		}
		System.out.println(loginID + "  " + fileName);

		// �����ͺ��̽��� ���� �̹��� ����
		adminJDBC = new JDBC();
		adminJDBC.originalImg(loginID, fileName);

		File orgFile = new File(Constants.IMG_PATH + loginID + "/filter/" + fileName); // ���������ּ�
		File newFile = new File(Constants.IMG_PATH + "sample.jpg"); // �����������ּ�

		// ���� ���� Ŭ���� ����
		copyFile(orgFile, newFile);
		
		
		// ���Ͽ� �����ֱ�
		ExposureFilter exposureFilter = new ExposureFilter(); // ���� ��ȭ (o)
		DitherFilter ditherFilter = new DitherFilter(); // ���� ����� (o)
		ThresholdFilter thresholdFilter = new ThresholdFilter(); // ����ȭ (o)
		LookupFilter lookupFilter = new LookupFilter(); // £�����(o)
		InvertFilter invertFilter = new InvertFilter(); // ���� (o)
		SolarizeFilter solarizeFilter = new SolarizeFilter(); // ��������(o)
		GrayFilter grayFilter = new GrayFilter(); // ���ϰ� (o)
		GrayscaleFilter grayscaleFilter = new GrayscaleFilter(); // ��� (o)
		ChromeFilter chromeFilter = new ChromeFilter(); //
		LensBlurFilter lensBlurFilter = new LensBlurFilter();

		System.out.println("�ּ� : " + Constants.IMG_PATH + loginID + "/filter/" + fileName);
		orgImage = ImageIO.read(new File(Constants.IMG_PATH + loginID + "/filter/" + fileName)); // ���������о����
		fileterImage = ImageIO.read(new File(Constants.IMG_PATH + "sample.jpg")); // �������������о����

		String path = Constants.IMG_PATH + loginID + "/filter/";

		fileterImage = exposureFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "ExposureFilter.jpg"));
		
		fileterImage = grayFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "GrayFilter.jpg"));

		fileterImage = ditherFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "DitherFilter.jpg"));

		fileterImage = lookupFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "LookupFilter.jpg"));

		fileterImage = grayscaleFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "GrayscaleFilter.jpg"));

		fileterImage = thresholdFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "ThresholdFilter.jpg"));

		fileterImage = chromeFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "ChromeFilter.jpg"));

		fileterImage = invertFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "InvertFilter.jpg"));

		fileterImage = solarizeFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "SolarizeFilter.jpg"));

		fileterImage = lensBlurFilter.filter(orgImage, fileterImage);
		ImageIO.write(fileterImage, "jpg", new File(path + "LensBlurFilter.jpg"));

		result.append("ExposureFilter.jpg,");
		result.append("GrayFilter.jpg,");
		result.append("DitherFilter.jpg,");
		result.append("LookupFilter.jpg,");
		result.append("GrayscaleFilter.jpg,");
		result.append("ThresholdFilter.jpg,");
		result.append("ChromeFilter.jpg,");
		result.append("InvertFilter.jpg,");
		result.append("SolarizeFilter.jpg,");
		result.append("LensBlurFilter.jpg,");
		
		System.out.println(result.toString());
		
		// Ŭ���̾�Ʈ���� �Ϸ�Ǿ��ٰ� ������
		resp.getWriter().print(result.toString());
	}
	
	@SuppressWarnings("resource")
	public static void copyFile(File orgFile, File newFile) throws IOException {
		// ���� ���� �˻��ϰ� ������ ���ο� ���� ����
		if (!newFile.exists()) {
			newFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(orgFile).getChannel();
			destination = new FileOutputStream(newFile).getChannel();

			long count = 0;
			long size = source.size();
			while ((count += destination.transferFrom(source, count, size - count)) < size)
				;
		} finally {
			// ���� �ݾ��ֱ�
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
}
