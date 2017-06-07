package server;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;

public class ImgSave {
	JDBCDriver adminJDBC;
	String loginID, imgAddress;
	EstimationAnalysis emotion;
	int faceCount; // 얼굴의 갯수를 카운트 하기 위한 변수
	
	@SuppressWarnings("static-access")
	public void imgSave(String[] array, HttpServletResponse resp) throws IOException{
		faceCount = 0;
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("filename")){
				imgAddress = array[j+2];
				// 기본 emotion을 neutral로 초기화
				emotion = new EstimationAnalysis(imgAddress);
			}else if(array[j].equals("response")){
				for(int k=j; k < array.length; k++){
					if(array[k].equals("faceRectangle\\")){
						faceCount++;
//						System.out.println("얼굴의 갯수"+faceCount);
					}
				}
				emotion.analysis(array, faceCount);
			}
		}
		
		try {
			adminJDBC = new JDBCDriver();
			adminJDBC.saveImg(loginID, imgAddress);
			
			// 클라이언트에게 완료되었다고 응답함
			resp.getWriter().print(Constants.SUCCESS);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("무결성 제약 조건에 위배됩니다.");
		}	
	}
}
