package server;

import java.sql.SQLIntegrityConstraintViolationException;

public class ImgSave {
	JDBC adminJDBC;
	String loginID, imgAddress;
	EstimationAnalysis emotion;
	int faceCount; // 얼굴의 갯수를 카운트 하기 위한 변수
	
	@SuppressWarnings("static-access")
	public void imgSave(String[] array){
		faceCount = 0;
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("filename")){
				imgAddress = array[j+2];
			}else if(array[j].equals("response")){
				for(int k=j; k < array.length; k++){
					if(array[k].equals("faceRectangle\\")){
						faceCount++;
//						System.out.println("얼굴의 갯수"+faceCount);
					}
				}
				emotion = new EstimationAnalysis(imgAddress);
				emotion.analysis(array, faceCount);
			}
		}
		
		try {
			adminJDBC = new JDBC();
			adminJDBC.saveImg(loginID, imgAddress);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("무결성 제약 조건에 위배됩니다.");
		}	
	}
}
