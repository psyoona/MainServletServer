package server;

import java.sql.SQLIntegrityConstraintViolationException;

public class ImgSave {
	JDBC adminJDBC;
	String loginID, imgAddress;
	EstimationAnalysis emotion;
	int faceCount; // ���� ������ ī��Ʈ �ϱ� ���� ����
	
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
//						System.out.println("���� ����"+faceCount);
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
			System.out.println("���Ἲ ���� ���ǿ� ����˴ϴ�.");
		}	
	}
}
