package server;

import java.awt.image.BufferedImage;

import img.ImageResize;
import img.ImageResizeFour;
import img.ImageResizeThree;
import img.ImageResizeTwo;

public class MakeAlbum {
	String[] fileName;
	String[] fileTwo;
	String[] fileThree;
	String[] fileFour;
	String frame;
	
	BufferedImage result1;
	BufferedImage result2;
	
	int count;
	String loginID, selectEmotion;
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void makeAlbum(String[] array, int index){
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
		
		// 사진의 emotion의 중 큰 값을 구해서 가져온다.
		adminJDBC = new JDBC();
		
		try {
			selectEmotion = adminJDBC.getEmotion(fileName, count);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		// 이미지 리사이즈 이후 자동으로 Merge까지 완료한다.
		ImageResize imageResizeTwo = null;
		ImageResize imageResizeThree = null;
		ImageResize imageResizeFour = null;
		
		if(frame.equals("2,2")){
			imageResizeTwo = new ImageResizeTwo();
			result1 = imageResizeTwo.resize(fileName[0], fileName[1], selectEmotion, loginID+"/");
			imageResizeTwo = new ImageResizeTwo();
			result2 = imageResizeTwo.resize(fileName[2], fileName[3], selectEmotion, loginID+"/");
			// 밑에 
			
		}else if(frame.equals("2,3")){
			System.out.println("print!");
			imageResizeTwo = new ImageResizeTwo();
			System.out.println("1: "+fileName[0]);
			System.out.println("2: "+fileName[1]);
			result1 = imageResizeTwo.resize(fileName[0], fileName[1], selectEmotion, loginID+"/");
			imageResizeThree = new ImageResizeThree();
			System.out.println("3: "+fileName[2]);
			System.out.println("4: "+fileName[3]);
			System.out.println("5: "+fileName[4]);
			result2 = imageResizeThree.resize(fileName[2], fileName[3], fileName[4], selectEmotion, loginID+"/");
			System.out.println("print End!");
			
		}else if(frame.equals("2,4")){
			imageResizeTwo = new ImageResizeTwo();
			result1 = imageResizeTwo.resize(fileName[0], fileName[1], selectEmotion, loginID+"/");
			imageResizeFour = new ImageResizeFour();
			result2 = imageResizeFour.resize(fileName[2], fileName[3], fileName[4], fileName[5], selectEmotion, loginID+"/");
			
		}else if(frame.equals("3,2")){
//			imageResize = new ImageResizeThree();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeTwo();
//			result2 = imageResize.resize(fileName[3], fileName[4], selectEmotion, loginID+"/");
//			
//		}else if(frame.equals("3,3")){
//			imageResize = new ImageResizeThree();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeThree();
//			result2 = imageResize.resize(fileName[3], fileName[4], fileName[5], selectEmotion, loginID+"/");
//			
//		}else if(frame.equals("3,4")){
//			imageResize = new ImageResizeThree();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeFour();
//			result2 = imageResize.resize(fileName[3], fileName[4], fileName[5], fileName[6], selectEmotion, loginID+"/");
//			
//		}else if(frame.equals("4,2")){
//			imageResize = new ImageResizeFour();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], fileName[3], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeTwo();
//			result2 = imageResize.resize(fileName[4], fileName[5], selectEmotion, loginID+"/");
//			
//		}else if(frame.equals("4,3")){
//			imageResize = new ImageResizeFour();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], fileName[3], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeThree();
//			result2 = imageResize.resize(fileName[4], fileName[5], fileName[6], selectEmotion, loginID+"/");
//			
//		}else if(frame.equals("4,4")){
//			imageResize = new ImageResizeFour();
//			result1 = imageResize.resize(fileName[0], fileName[1], fileName[2], fileName[3], selectEmotion, loginID+"/");
//			imageResize = new ImageResizeFour();
//			result2 = imageResize.resize(fileName[4], fileName[5], fileName[6], fileName[7], selectEmotion, loginID+"/");
			
		}						
	}
}
