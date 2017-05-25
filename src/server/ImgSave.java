package server;

import java.sql.SQLIntegrityConstraintViolationException;

public class ImgSave {
	JDBC adminJDBC;
	String loginID, imgAddress;
	EstimationAnalysis emotion;
	
	@SuppressWarnings("static-access")
	public void imgSave(String[] array){
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("filename")){
				imgAddress = array[j+2];
			}else if(array[j].equals("faceRectangle\\")){
				emotion = new EstimationAnalysis(imgAddress);
				emotion.analysis(array);
			}
		}
		
		try {
			adminJDBC = new JDBC();
			adminJDBC.saveImg(loginID, imgAddress);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("���Ἲ ���� ���ǿ� ����˴ϴ�.");
		}	
	}
}
