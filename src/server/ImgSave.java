package server;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletResponse;

import JDBC.JDBCDriver;

public class ImgSave {
	JDBCDriver adminJDBC;
	String loginID, imgAddress;
	EstimationAnalysis emotion;
	int faceCount; // ���� ������ ī��Ʈ �ϱ� ���� ����
	
	@SuppressWarnings("static-access")
	public void imgSave(String[] array, HttpServletResponse resp) throws IOException{
		faceCount = 0;
		for(int j = 0; j < array.length; j++){
			if(array[j].equals("id")){
				loginID = array[j+2];
			}else if(array[j].equals("filename")){
				imgAddress = array[j+2];
				// �⺻ emotion�� neutral�� �ʱ�ȭ
				emotion = new EstimationAnalysis(imgAddress);
			}else if(array[j].equals("response")){
				for(int k=j; k < array.length; k++){
					if(array[k].equals("faceRectangle\\")){
						faceCount++;
//						System.out.println("���� ����"+faceCount);
					}
				}
				emotion.analysis(array, faceCount);
			}
		}
		
		try {
			adminJDBC = new JDBCDriver();
			adminJDBC.saveImg(loginID, imgAddress);
			
			// Ŭ���̾�Ʈ���� �Ϸ�Ǿ��ٰ� ������
			resp.getWriter().print(Constants.SUCCESS);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("���Ἲ ���� ���ǿ� ����˴ϴ�.");
		}	
	}
}
