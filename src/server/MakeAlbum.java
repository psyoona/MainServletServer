package server;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;
import img.ImageResize;
import img.ImageResizeFour;
import img.ImageResizeOne;
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
	JDBCDriver adminJDBC;
	
	@SuppressWarnings("static-access")
	public void makeAlbum(String[] array, int index, HttpServletResponse resp) throws IOException{
		String[] fileName = new String[10];
		count = 0;
		for(int j = index; j <array.length; j++){
			if(array[j].equals("filename")){
				System.out.println(array[j+2]);
				char first = array[j+2].charAt(0);
				count = Integer.valueOf(first)-48;
				fileName[count] = array[j+2];
			}else if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("frame")){
				frame = array[j+2];
				System.out.println(frame);
			}
		} // End of for
		
		// 사진의 emotion의 중 큰 값을 구해서 가져온다.
		adminJDBC = new JDBCDriver();
		
		// 이미지 리사이즈 이후 자동으로 Merge까지 완료한다.
		ImageResize imageResizeOne = null;
		ImageResize imageResizeTwo = null;
		ImageResize imageResizeThree = null;
		ImageResize imageResizeFour = null;
		
		try {
			if(frame.equals("1,2")){				
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.SECOND], fileName[Constants.THIRD]);
				
				imageResizeOne = new ImageResizeOne();
				result1 = imageResizeOne.resize(Constants.LEFT, fileName[Constants.FIRST], firstEmotion, loginID);
				imageResizeTwo = new ImageResizeTwo();
				result2 = imageResizeTwo.resize(Constants.RIGHT, fileName[Constants.SECOND], fileName[Constants.THIRD], secondEmotion, loginID);
				
			}else if(frame.equals("1,3")){
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.SECOND], fileName[Constants.THIRD], fileName[Constants.FORTH]);
				
				imageResizeOne = new ImageResizeOne();
				result1 = imageResizeOne.resize(Constants.LEFT, fileName[Constants.FIRST], firstEmotion, loginID);
				imageResizeTwo = new ImageResizeTwo();
				result2 = imageResizeTwo.resize(Constants.RIGHT, fileName[Constants.SECOND], fileName[Constants.THIRD], fileName[Constants.FORTH], secondEmotion, loginID);
				
			}else if(frame.equals("1,4")){
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.SECOND], fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH]);
				
				imageResizeOne = new ImageResizeOne();
				result1 = imageResizeOne.resize(Constants.LEFT, fileName[Constants.FIRST], firstEmotion, loginID);
				imageResizeFour = new ImageResizeFour();
				result2 = imageResizeFour.resize(Constants.RIGHT, fileName[Constants.SECOND], fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH], secondEmotion, loginID);
				
			}else if(frame.equals("2,3")){				
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST], fileName[Constants.SECOND]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH]);
				
				imageResizeTwo = new ImageResizeTwo();
				result1 = imageResizeTwo.resize(Constants.LEFT, fileName[Constants.FIRST], fileName[Constants.SECOND], firstEmotion, loginID);
				imageResizeThree = new ImageResizeThree();
				result2 = imageResizeThree.resize(Constants.RIGHT, fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH], secondEmotion, loginID);
				
			}else if(frame.equals("2,4")){
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST], fileName[Constants.SECOND]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH], fileName[Constants.SIXTH]);
				
				imageResizeTwo = new ImageResizeTwo();
				result1 = imageResizeTwo.resize(Constants.LEFT, fileName[Constants.FIRST], fileName[Constants.SECOND], firstEmotion, loginID);
				imageResizeFour = new ImageResizeFour();
				result2 = imageResizeFour.resize(Constants.RIGHT, fileName[Constants.THIRD], fileName[Constants.FORTH], fileName[Constants.FIFTH], fileName[Constants.SIXTH], secondEmotion, loginID);
				
			}else if(frame.equals("3,4")){
				firstEmotion = adminJDBC.getEmotion(fileName[Constants.FIRST], fileName[Constants.SECOND], fileName[Constants.THIRD]);
				secondEmotion = adminJDBC.getEmotion(fileName[Constants.FORTH], fileName[Constants.FIFTH], fileName[Constants.SIXTH], fileName[Constants.SEVENTH]);
				
				imageResizeThree = new ImageResizeThree();
				result1 = imageResizeThree.resize(fileName[Constants.FIRST], fileName[Constants.SECOND], fileName[Constants.THIRD], firstEmotion, loginID);		
				imageResizeFour = new ImageResizeFour();
				result2 = imageResizeFour.resize(Constants.RIGHT, fileName[Constants.FORTH], fileName[Constants.FIFTH], fileName[Constants.SIXTH], fileName[Constants.SEVENTH], secondEmotion, loginID);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		@SuppressWarnings("unused")
		// 최종 결과물을 토대로 사진을 합성함
		TotalMerge total = new TotalMerge(result1, result2, loginID);
		
		// 클라이언트에게 작업이 완료되었다고 응답함
		resp.getWriter().print(Constants.SUCCESS);
	}
}
