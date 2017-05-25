package server;

import img.ImageResize;
import img.ImageResizeFour;
import img.ImageResizeThree;
import img.ImageResizeTwo;

public class MakeAlbum {
	String[] fileName;
	int count;
	String loginID, selectEmotion;
	JDBC adminJDBC;
	
	@SuppressWarnings("static-access")
	public void makeAlbum(String[] array, int index){
		String[] fileName = new String[5];
		count = 0;
		for(int j = index; j <array.length; j++){
			if(array[j].equals("filename")){
				System.out.println(array[j+2]);
				fileName[count] = array[j+2];
				count++;
			}else if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("frame")){
				
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
		
		ImageResize imageResize = null;
		if(count+1 == 3){
			imageResize = new ImageResizeTwo();
			imageResize.resize(fileName, selectEmotion, loginID+"/");
		}else if(count+1 == 4){
			imageResize = new ImageResizeThree();
			imageResize.resize(fileName, selectEmotion, loginID+"/");
		}else if(count+1 == 5){
			imageResize = new ImageResizeFour();
			imageResize.resize(fileName, selectEmotion, loginID+"/");
		}					
	}
}
