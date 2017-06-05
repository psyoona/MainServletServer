package server;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBC;
import img.ImageResize;
import img.ImageResizeFour;
import img.ImageResizeThree;
import img.ImageResizeTwo;
import img.TotalMerge;

public class MakeAlbum {
	String[] fileName;
	String[] fileTwo;
	String[] fileThree;
	String[] fileFour;
	String frame;
	
	BufferedImage result1;
	BufferedImage result2;
	
	int count;
	String loginID, firstEmotion, secondEmotion;
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void makeAlbum(String[] array, int index, HttpServletResponse resp) throws IOException{
		String[] fileName = new String[10];
		count = 0;
		for(int j = index; j <array.length; j++){
			if(array[j].equals("filename")){
				System.out.println(array[j+2]);
				fileName[count] = array[j+2];
				count++;
			}else if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("frame")){
				frame = array[j+2];
				System.out.println(frame);
			}
		} // End of for
		
		// ������ emotion�� �� ū ���� ���ؼ� �����´�.
		adminJDBC = new JDBC();
		
		try {
			if(frame.equals("2,3")){
				firstEmotion = adminJDBC.getEmotion(fileName[0], fileName[1]);
				secondEmotion = adminJDBC.getEmotion(fileName[0], fileName[1], fileName[2]);
				System.out.println("�����ͺ��̽����� ������ ��(2��)"+ firstEmotion);
				System.out.println("�����ͺ��̽����� ������ ��(3��)"+ secondEmotion);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}				
		
		// �̹��� �������� ���� �ڵ����� Merge���� �Ϸ��Ѵ�.
		ImageResize imageResizeTwo = null;
		ImageResize imageResizeThree = null;
		ImageResize imageResizeFour = null;
		
		if(frame.equals("1,2")){
			
		}else if(frame.equals("1,3")){
			
		}else if(frame.equals("1,4")){	
			
		}else if(frame.equals("2,3")){
			System.out.println("print!");
			imageResizeTwo = new ImageResizeTwo();
			result1 = imageResizeTwo.resize(fileName[0], fileName[1], firstEmotion, loginID);
			imageResizeThree = new ImageResizeThree();
			result2 = imageResizeThree.resize(fileName[2], fileName[3], fileName[4], secondEmotion, loginID);
			System.out.println("print End!");
			
		}else if(frame.equals("2,4")){
			imageResizeTwo = new ImageResizeTwo();
			result1 = imageResizeTwo.resize(fileName[0], fileName[1], firstEmotion, loginID);
			imageResizeFour = new ImageResizeFour();
			result2 = imageResizeFour.resize(fileName[2], fileName[3], fileName[4], fileName[5], secondEmotion, loginID);
		}else if(frame.equals("3,4")){
			
			
		}						
		@SuppressWarnings("unused")
		// ���� ������� ���� ������ �ռ���
		TotalMerge total = new TotalMerge(result1, result2, loginID);
		
		// Ŭ���̾�Ʈ���� �۾��� �Ϸ�Ǿ��ٰ� ������
		resp.getWriter().print(Constants.SUCCESS);
	}
}
