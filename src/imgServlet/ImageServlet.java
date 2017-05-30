package imgServlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Constants;

public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	String loginID;
	String fileName;
	
	public void showImage(HttpServletRequest req, HttpServletResponse resp, String filePath) throws IOException{
		resp.setContentType("image/gif");

		byte[] by = new byte[4096]; //�ѹ��� �о�� ����ũ�� 1024 ����Ʈ
		System.out.println("get!");
		
		loginID = req.getParameter("id");
		fileName = req.getParameter("fileName");
		
		System.out.println(loginID);
		System.out.println(fileName);
		
		
		//fileName = fileName.substring(9, fileName.length()-5);
		//System.out.println(fileName.length()-5);
		
		// �����ͺ��̽����� �α��� ���̵�� ���� ��
		

		//��������� OutputStream ��ü
		ServletOutputStream out = resp.getOutputStream();
		try {
			//�̹��� �ּ� ����
			//String imagePath = getServletContext().getRealPath("")+"images\\aa.jpg";
			StringBuilder imgPath = new StringBuilder();
			imgPath.append(Constants.IMG_PATH);
			imgPath.append(loginID);
			imgPath.append("/final/");
			imgPath.append(fileName);
			imgPath.append(".jpg");			
			
			System.out.println("������ �̹��� ��� : " + imgPath.toString());
			
			@SuppressWarnings("resource")
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(imgPath.toString()));
			
			//����(in)�� �ִ� �����͸� 2048����Ʈ(by) ��ŭ �о���� �����Ͱ� ������� �ݺ��� ����
			while(in.read(by) != -1) {
				out.write(by); //2048����Ʈ�� ���
			}
					
			//in.close();			
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
		
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filePath = "test";
		showImage(req, resp, filePath);
	}	
	
	void showImage(String id){
		
	}
}